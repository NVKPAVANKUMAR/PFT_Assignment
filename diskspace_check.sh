i=1
outputs_per_line=10
frequency=30
while true; do
  echo -n "$(df / | awk 'NR==2 {print $5}') "
  if [ $((i%outputs_per_line)) -eq 0 ]; then
    echo
  fi
  ((i++))
  sleep "$frequency"
done