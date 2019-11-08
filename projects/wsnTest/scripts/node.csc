set RT \
set t 0
set PT 3
set MT \
set hasIncreasedConfig false
set shouldSendFireMessage true

loop
    pulseTimer $t 0.01 timeReached
    if ($timeReached == true)
        updatePulseTable PT
        removeDeadNodes PT RT shouldCreateConfig
        if($shouldCreateConfig == true)
            createConfig RT configPacket
            print Pulse_config
            send !color 3
            send $configPacket
        end
        createPulse pulsePacket
        print Pulse
        send !color 4
        send $pulsePacket
    end

    dReadSensor isOnFire
    if(($isOnFire == 1) && ($shouldSendFireMessage == true))
        set shouldSendFireMessage false
        getRelayList RT relayList
        while($relayList != \)
            getNextRelay relayList relay
            createDataPackage $relay rawDataPacket
            decipher $rawDataPacket dataPacket type senderNode
            addToMT MT $dataPacket $senderNode
            getNextHop RT MT $dataPacket destinationNode

            print i´m_on_fire
            send !color 2
            send $rawDataPacket $destinationNode
        end
    end

    battery batteryLevel
    if(($batteryLevel < 5000) && ($hasIncreasedConfig == false))
        set hasIncreasedConfig true
        updateRoutingTableLowBattery RT shouldCreateConfig
        if ($shouldCreateConfig==true)
            createConfig RT configPacket
            print low_battery_config
            send !color 1
            send $configPacket
        end 
    end

    read rawData

    if ($rawData!=\)
        decipher $rawData data dataType senderNode
        if ($dataType==0)
            updateRoutingTable RT $data shouldCreateConfig
            if ($shouldCreateConfig==true)
                createConfig RT configPacket
                print Config
                send !color 1
                send $configPacket
            end 
            registerPulseForConfig PT $senderNode $RT
        end
        if ($dataType==1)
            set destinationNode \
            isInMT MT $data isInMT
            if($isInMT == true)
                getNextHop RT MT $data destinationNode
            else
                addToMT MT $data $senderNode
                getNextHop RT MT $data destinationNode                
            end

            resignData $data dataPacket 

            print data
            send !color 2            
            send $dataPacket $destinationNode
        end
        if ($dataType==2)
            isInRT RT $data isNodeInRT
            getSender $data node
            registerPulse PT $node
            if($RT!=\)
                if ($isNodeInRT==false)
                    createConfig RT configPacket
                    print pulse_config2
                    send !color 3
                    send $configPacket $node
                end
            end
        end
        if ($dataType==3)
            getReceivers MT $data receiverList anyRemaining
            while($anyRemaining==true)
                getNextReceiver receiverList receiver anyRemaining
                resignAck $data ackPacket

                print received_ack
                send !color 7
                send $ackPacket $receiver
            end
        end
    end
    delay 100