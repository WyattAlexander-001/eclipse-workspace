BEGIN {
    FS=",";  # Set field separator as comma for CSV file
    print "Sales Data:";
}

{
    name = $1;     # First column: Item name
    quantity = $2; # Second column: Quantity sold
    price = $3;    # Third column: Price per item
    total = quantity * price;
    print name " - Total Sales: $" total;
}

END {
    print "End of Sales Report";
}
