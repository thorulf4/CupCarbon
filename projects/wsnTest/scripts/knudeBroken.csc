set RT \
set t 0
set DT 3
set hasSent 0

createPulse p
print pulse
send !color 4
send $p

loop

    read tx

    if ($tx!=\)
        decipher $tx x y senderNode
        if ($y==0)
            print test
            if ($hasSent == 0)
                updateRoutingTable RT $x z
                if ($z==true)
                    createConfig RT p
                    send !color 10
                    send $p
                end 
                set hasSent 1
            end
        end
    end
    delay 10