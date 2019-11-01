set CT \

loop
    wait
    read x
    decipher $x x y

    if($y==2)
        checkConfiguredNodes CT $x z
        if($z==false)
            createRelayConfig p
            send $p
        end
    end
    if($y==1)
        getData $x d
        print $d
    end