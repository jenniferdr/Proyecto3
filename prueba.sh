#!/bin/bash

#Probar los archivos en dimacs
base=/home/gmljosea/Codigo/Algoritmos3/Proyecto3

cd $base/random
for i in $(ls)
do
	cd $base/random/$i
	for j in $(ls *.input)
	do
		nombre=$(echo $j | cut -d . -f 1)
		#echo $nombre
		cd $base
		rm ./random/$i/$nombre.salida
		touch ./random/$i/$nombre.salida
		java Main ./random/$i/$nombre.input ./random/$i/$nombre.salida 2> ./random/$i/$nombre.salida
		if (diff ./random/$i/$nombre.output ./random/$i/$nombre.salida >> /dev/null)
		then
			echo "Exito en el caso $nombre"
		else
			echo "Error en el caso $nombre"
		fi
	done
done