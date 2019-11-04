set RT \
set t 0
set DT 3

loop
    pulseTimer $t 0.01 z
    if ($z==true)
        updatePulseTable DT
        removeDeadNodes DT RT shouldCreateConfig
        if(shouldCreateConfig == true)
            createConfig RT p
            print Pulse_config
            send !color 3
            send $p
        end
        createPulse p
        print Pulse
        send !color 4
        send $p
    end

    read tx

    if ($tx!=\)
        decipher $tx x y
        if ($y==0)
            updateRoutingTable RT $x z
            if ($z==true)
                createConfig RT p
                print Config
                send !color 1
                send $p
            end 
        end
        if ($y==1)
            print data
            findNextHop RT $x k
            send !color 2
            send $tx $k
        end
        if ($y==2)
            isInRT RT $x z
            getSender $x k
            registerPulse DT $k
            if($RT!=\)
                if ($z==false)
                    createConfig RT p
                    print pulse_config2
                    send !color 3
                    send $p $k
                end
            end
        end
    end
    delay 10