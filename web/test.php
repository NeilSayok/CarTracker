<?php

if(retFalse() || retTrue()) {
    print "Out A: true\n";
} else {
    print "Out A: false\n";
}

if(retFalse() || !retFalse()) {
    print "Out B: false\n";
} else {
    print "Out B: true\n";
}




function retFalse(){
    print "retFalse false\n";
    return false;
}

function retTrue(){
    print "retTrue true\n";
    return true;
}

