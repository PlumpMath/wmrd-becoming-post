for n in ?_????.jpg; do echo $n ; convert $n -rotate -90 -quality 100 -adaptive-sharpen 4 -crop 1080x1920+0+0 +repage ${n%.jpg}_flat.jpg; done

for n in ?_????.jpg; do echo $n ; convert $n -rotate -90 -quality 100 -adaptive-sharpen 4 -crop 600x600+300+600 +repage ${n%.jpg}_flat_cropped.jpg; done

montage a*_flat_cropped.jpg -geometry "600x600>+1+1" -tile 7x -background "#000000" a_all_full_cropped.tif 

montage b*_flat_cropped.jpg -geometry "600x600>+1+1" -tile 7x -background "#000000" b_all_full_cropped.tif 

montage c*_flat_cropped.jpg -geometry "600x600>+1+1" -tile 7x -background "#000000" c_all_full_cropped.tif 

montage a*_flat.jpg b*_flat.jpg c*_flat.jpg -geometry "1080x1920>+4+4" -tile x3 -background "#000000" abc_all_full.tif

convert abc_all_full.tif -resize 20% abc_all_20.jpg

convert abc_all_full.tif -resize 5% abc_all_05.jpg

montage a*[0,5]_flat_cropped.jpg -geometry "600x600>+1+1" -tile 3x -background "#000000" a_all_full_cropped_sparse.tif

montage b*[0,5]_flat_cropped.jpg -geometry "600x600>+1+1" -tile 3x -background "#000000" b_all_full_cropped_sparse.tif

montage c*[0,5]_flat_cropped.jpg -geometry "600x600>+1+1" -tile 3x -background "#000000" c_all_full_cropped_sparse.tif


