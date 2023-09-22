
# This is a comment

BEGIN {
    print "Hello, World!"
    x = 42
    str = "test string"
}

{
    print "Line number:", NR
    print $0
}

END {
    print "That's all, folks!"
}
