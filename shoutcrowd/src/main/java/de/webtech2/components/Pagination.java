package de.webtech2.components;

import de.webtech2.util.PaginationItem;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Pagination {
    
    @Inject
    private ComponentResources resources;

    @Property
    private String srcPage;

    @Parameter(required = true)
    private List<PaginationItem> list;
    @Parameter(required = true)
    private PaginationItem item;
    @Parameter(required = true)
    private int page;
    @Parameter(required = true)
    private int amount;
    @Parameter
    private String format = "first,previous,pages,next,last,bottom,top";
    
    @Property
    private List<Integer> pageList;
    @Property
    private Integer pageItem;
    
    private int lastPage;
    private List subList;
    private int subListSize = 0;
    
    private boolean doRenderTopPaginator = false;
    private boolean doRenderBottomPaginator = false;
    
    private List<String> formatList;
    
    private void setupRender() {
        
        // Extract Format-Definitions
        this.formatList = Arrays.asList(this.format.split(","));

        // Calculate Start-Index
        int start = ((this.page - 1) * this.amount);
        if (start < 0) {
            start = 0;
        }

        // Calculate End-Index
        int end = start + this.amount;
        if (end > this.list.size()) {
            end = this.list.size();
        }

        // Generate SubList from Parameter List by start/end-Index
        this.subList = new LinkedList<PaginationItem>();
        for (int j = start; j < end; j++) {
            this.subList.add(this.list.get(j));
            this.subListSize++;
        }

        // Calculate possible Pages
        this.pageList = new LinkedList<Integer>();
        this.lastPage = (int) Math.ceil((double) this.list.size() / (double) amount);
        for (int i = 1; i <= this.lastPage; i++) {
            pageList.add(new Integer(i));
        }

    }

    void beginRender() {
        // Decide to Render Top-Pagination
        if (this.subList.size() == this.subListSize && this.formatList.contains("top")) {
            this.doRenderTopPaginator = true;
        } else {
            this.doRenderTopPaginator = false;
        }
        
        // Initialize Item
        if (this.subList.size() > 0) {
            this.item = (PaginationItem) this.subList.get(0);
            this.subList.remove(0);
        }
        
        // Decide to Render Bottom-Pagination
        if (this.subList.isEmpty() && this.formatList.contains("bottom")) {
            this.doRenderBottomPaginator = true;
        } else {
            this.doRenderBottomPaginator = false;
        }
        
    }

    boolean afterRender() {
        if (this.subList.size() <= 0) {
            return true;
        }
        return false;
    }
    
    public boolean isSelectedPage() {
        if (this.page == this.pageItem) {
            return true;
        }
        return false;
    }
    
    public boolean isRenderFirst() {
        if (this.page > 1 && this.formatList.contains("first")) {
            return true;
        }
        return false;
    }

    public boolean isRenderLast() {
        if (this.page < this.lastPage && this.formatList.contains("last")) {
            return true;
        }
        return false;
    }

    public boolean isRenderPrevious() {
        if (this.page > 1 && this.formatList.contains("previous")) {
            return true;
        }
        return false;
    }

    public boolean isRenderNext() {
        if (this.page < this.lastPage && this.formatList.contains("next")) {
            return true;
        }
        return false;
    }
    
    public boolean isRenderPages() {
        if (this.formatList.contains("pages")) {
            return true;
        }
        return false;
    }
    
    public boolean isRenderTopPaginator() {
        return this.doRenderTopPaginator;
    }
    
    public boolean isRenderBottomPaginator() {
        return this.doRenderBottomPaginator;
    }
}
