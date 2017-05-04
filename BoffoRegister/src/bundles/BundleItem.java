/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bundles;

import database.BoffoDbObject;

/**
 *
 * @author tcox5
 */
public class BundleItem extends BoffoDbObject {
    private String bundleID;
    private String productID;
    private int qty;
    private static final String tableName = "bundle_items_tbl";
}
