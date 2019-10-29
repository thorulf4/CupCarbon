set RT \

loop
 wait
 read tx
 
 decipher $tx x y
 if ($y==0)
  updateRoutingTable RT $x z
  if ($z==true)
   createConfig RT p
   send $p
  end 
 end
 if ($y==1)
  print data
  findNextHop RT $x k
  send $tx $k
 end
 if ($y==2)
  print Pulse

 end
