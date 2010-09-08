#!/bin/sh

RUN_JAR='java -hotspot -jar'

for file in example_bots/*.jar
do
    player_1_counter=0
    player_2_counter=0
    for i in {1..100}
    do
        RES=`${RUN_JAR} tools/PlayGame.jar maps/map${i}.txt 3000 1000 log.txt "${RUN_JAR} $file" "${RUN_JAR} MyBot.jar" 2>&1 | grep ^Player`
        if [ "${RES}" = "Player 1 Wins!" ] ; then
            player_1_counter=`expr ${player_1_counter} + 1`
        else
            player_2_counter=`expr ${player_2_counter} + 1`
        fi
    done
    echo "${file} : ${player_2_counter}/100"
done
