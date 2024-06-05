package inventory_management.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse {

    private List<?> page;

    private int totalPages;

    private long totalElements;

    private int size;

    private int numberOfElements;

    private int number;

    public PageResponse(List<?> list, Page<?> page) {
        this.page = list;
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.size = page.getSize();
        this.numberOfElements = page.getNumberOfElements();
        this.number = page.getNumber();
    }
}
