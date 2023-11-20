BEGIN {
    print "Starting AWK Processing"
}

{
    print $0
}

END {
    print "Finished AWK Processing"
}
