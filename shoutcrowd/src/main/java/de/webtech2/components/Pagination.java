
package de.webtech2.components;

import de.webtech2.util.PaginationItem;
import java.util.ArrayList;
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
    
    @Property
    @Parameter(required = true)
    private int page;
    
    @Property
    @Parameter(required = true)
    private int amount;
    
    @Property
    private int pageItem;
    
    private int maxPages;
    
    private void setupRender() {
        int start = ((this.page - 1) * (this.amount - 1));
        int end = start + this.amount;
        if (end > this.list.size()) {
            end = this.list.size();
        }
        this.subList = this.list.subList(start, end);
        double tmpFloat = (double) this.list.size() / (double) amount;
        this.maxPages = (int) Math.ceil(tmpFloat);
    }
    
    public Integer getCount() {
        return this.list.size();
    }
    
    public Integer getSubCount() {
        return this.subList.size();
    }
    
    public List<Integer> getAllPages() {
        List<Integer> pageList = new ArrayList<Integer>();
        for (int i = 1; i <= this.maxPages; i++) {
            pageList.add(new Integer(i));
        }
        return pageList;
    }
    
    public boolean getPrevPossible() {
        if (this.page - 1 <= 0) {
            return false;
        }
        return true;
    }
    
    public boolean getNextPossible() {
        if (this.page - 1 >= this.maxPages - 1) {
            return false;
        }
        return true;
    }
}
