package bundles;

/*
Needed from other classes:

Utility Package:
    PairList<T>
    Pair<T>

Transaction Package:
    TicketElement

Receipt:
    private Pairlist<ReceiptElement> elements
    private Pairlist<Product> products
    public int add(Product p, int number)
        products.add(p, number)
        elements = Bundle.processBundles(products)
    public int remove(Product p, int number)
        products.remove(p, number)
        elements = Bundle.processBundles(products)

Product:
    extends TicketElement
    public Product clone()
    public static List<Product> getAllProducts()
    public static List<Product> getAllProducts(boolean enabled)

 */
