appdir=$(dirname $(readlink -f $0))
cd $appdir
java -jar $(ls --reverse $appdir/target/tilialog-*.jar | head -n 1) &
