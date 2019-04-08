echo "$(ps -ax | grep node)"
pids="$(ps -ax | grep node | sed 's/^ *//g'| cut -d " " -f 1 | tr "\n" " ")"
echo "Running command : kill -9 $pids"
kill -9 $pids
