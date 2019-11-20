set CT \

loop
    wait
    read rawData
    decipher $rawData data dataType senderNode

    if($dataType==2)
        checkConfiguredNodes CT $data isInTable
        if($isInTable==false)
            createRelayConfig configPacket
            print Config
            send !color 10
            send $configPacket $senderNode
        end
    end
    if($dataType==1)

        createAckPackage $data ackPackage

        getData $data d
        print $d

        send !color 7
        send $ackPackage $senderNode
    end