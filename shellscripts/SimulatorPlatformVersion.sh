instruments -s devices | grep "iPhone 7 (" | grep -i Simulator | grep -v + | awk -F "(" '{print $2}' | awk -F ")" '{print $1}'
