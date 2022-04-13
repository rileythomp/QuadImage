echo Creating gifs/${1}.gif...
echo This may take a few minutes
convert -resize 50% -loop 0 -delay 10 frames/${1}/{0..2}{0..9}{0..9}\.jpg gifs/${1}.gif
rm frames/${1}/*
echo Removed frames/${1}/\*
echo Created gifs/${1}.gif
