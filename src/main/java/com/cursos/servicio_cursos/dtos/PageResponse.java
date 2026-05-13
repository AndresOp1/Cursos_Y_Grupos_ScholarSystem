package com.cursos.servicio_cursos.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
  private List<T> content;
  private int currentPage;
  private int totalPages;
  private int pageSize;
  private long totalElements;
  private boolean isLast;
  private boolean isFirst;
}
