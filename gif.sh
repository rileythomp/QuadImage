echo Creating ${1}.gif...
convert -loop 0 -delay 10 ${1}/{0..2}{0..9}{0..9}\.jpg quadgifs/${1}.gif
rm ${1}/*
echo Created ${1}.gif
