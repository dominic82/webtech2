
package de.webtech2.components;

import de.webtech2.util.PaginationItem;
import java.util.List;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class Pagination {
    
    @Parameter(required = true)
    private List list;
    
    @Property
    private List subList;
    
    @Property
    private PaginationItem item;
    
    @Parameter(required = true)
    private int page;
    
    @Parameter(required = true)
    private int amount;
    
    void onActivate() {
        int start = ((this.page - 1) * this.amount);
        if (start > 0) {
            start--;
        }
        int end = start + this.amount;
        this.subList = this.list.subList(start, end);
    }
    
}
