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

    tickCongaTimer MT previousTime timedOutMessages
    while($timedOutMessages != \)
        iterate timedOutMessages timedOutMessage
        getDataFromMessageId $MT $timedOutMessage timedOutData
        getNextHop RT MT $timedOutData destinationNode
        resignData $timedOutData timedOutDataPackage
        print data_timed_out
        send !color 2
        send $timedOutDataPackage $destinationNode
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
            if($isInMT == false)                
                addToMT MT $data $senderNode
            end
            getNextHop RT MT $data destinationNode

            resignData $data dataPacket 

            print data_and_ack
            send !color 2            
            send $dataPacket $destinationNode

            delay 100

            createACK $data ackPackage
            send !color 7
            send $ackPackage $senderNode
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
            decreaseCongaStep MT $data shouldSend
            if($shouldSend == true)
                createACK $data ackPackage
                getSenderFromMessageTable $ackPackage $MT lastDataSender
                print ack
                send !color 7
                send $ackPackage $lastDataSender    
            end
        end
        if ($dataType==4)
            decreaseCongaStepRelay MT $data shouldSend
            if($shouldSend == true)
                createACK $data ackPackage
                getSenderFromMessageTable $ackPackage $MT lastDataSender
                print ack_from_relay
                send !color 7
                send $ackPackage $lastDataSender    
            end
        end
    end
    tickExpirationTimers MT
    delay 100