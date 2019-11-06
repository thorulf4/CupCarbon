set RT \
set t 0
set PT 3

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
            registerPulseForCongfig PT $senderNode $RT
        end
        if ($dataType==1)
            print data
            findNextHop RT $data node
            send !color 2
            send $rawData $node
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
    end
    delay 10