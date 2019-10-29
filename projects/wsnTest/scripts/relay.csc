createRelayConfig p
send $p

loop
 wait
 read x
 decipher $x x y

 if($y==2)
  print pulse
 end
 if($y==1)
  getData $x d
  print $d
 end