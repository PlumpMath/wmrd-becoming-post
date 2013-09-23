here="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

text=`date +"%A, %d %B"` ; convert $here/black.png -fill black -pointsize 40 -gravity southeast pango:"<span color='#fff' bgcolor='#000' font_family='CronosPro Light' font_size='40000' gravity='east'><b>Becoming</b> (time-lapse)\n$text</span>" -composite title.png

counter=1
ll="A"

inp1=$here/black.png
inp0=title.png
for blend in $(seq 0 5 95)
do	
	a0=seq.`printf "%04d" $counter`.jpg
	counter=$[$counter +1]
	composite $inp0 $inp1 -blend $blend $a0
done

inp0=$here/black.png
inp1=title.png
for blend in $(seq 0 5 95)
do	
	a0=seq.`printf "%04d" $counter`.jpg
	counter=$[$counter +1]
	composite $inp0 $inp1 -blend $blend $a0
done

inp0=a_0000_flat.jpg
inp1=$here/black1080x1920.png
for blend in $(seq 0 10 95)
do	
	a0=seq.`printf "%04d" $counter`.jpg
	counter=$[$counter +1]
	composite $inp0 $inp1 -blend $blend -resize 50% $a0
done


for n in a_????_flat.jpg
do
	if [ $ll != "A" ]
	then
		inp1=$ll
		inp0=$n
		for blend in $(seq 0 5 95)
		do	
			a0=seq.`printf "%04d" $counter`.jpg
			counter=$[$counter +1]
			composite $inp0 $inp1 -blend $blend -resize 50% $a0
		done

	fi
	echo $counter
	 
	inp1=$n
	inp0=b_${n#a_}

	for blend in $(seq 0 10 90)
	do
		a0=seq.`printf "%04d" $counter`.jpg
		counter=$[$counter +1]
		composite $inp0 $inp1 -blend $blend -resize 50% $a0
	done


	inp1=b_${n#a_}
	inp0=c_${n#a_}

	for blend in $(seq 0 10 100)
	do
		a0=seq.`printf "%04d" $counter`.jpg
		counter=$[$counter +1]
		composite $inp0 $inp1 -blend $blend -resize 50% $a0
	done

	ll=$inp0


done


inp1=$ll
inp0=$here/black1080x1920.png
for blend in $(seq 0 5 95)
do	
	a0=seq.`printf "%04d" $counter`.jpg
	counter=$[$counter +1]
	composite $inp0 $inp1 -blend $blend -resize 50% $a0
done


ffmpeg -r 24 -f image2  -i "seq.%04d.jpg" -r 24 -q:v 0 seq.avi
