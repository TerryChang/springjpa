package com.terry.springjpa.common.tld;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings("serial")
public class PaginationTag extends TagSupport{

	private int page_no;                    // 현재 보는 페이지 번호
    private int total_cnt;                  // 총 레코드 갯수
    private int page_size;                  // 한 페이지를 구성하는 레코드 갯수
    private int page_group_size;            // 한 페이지 그룹을 구성하는 페이지 갯수
    private String jsFunction;              // 클릭시 실행되는 자바스크립트 함수명
    private boolean zerobased = false;		// 1페이지를 0부터 시작할것인지의 여부
    
	public int getPage_no() {
		return page_no;
	}

	public void setPage_no(int page_no) {
		this.page_no = page_no;
	}

	public int getTotal_cnt() {
		return total_cnt;
	}

	public void setTotal_cnt(int total_cnt) {
		this.total_cnt = total_cnt;
	}

	public int getPage_size() {
		return page_size;
	}

	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}

	public int getPage_group_size() {
		return page_group_size;
	}

	public void setPage_group_size(int page_group_size) {
		this.page_group_size = page_group_size;
	}

	public String getJsFunction() {
		return jsFunction;
	}

	public void setJsFunction(String jsFunction) {
		this.jsFunction = jsFunction;
	}

	public boolean isZerobased() {
		return zerobased;
	}

	public void setZerobased(boolean zerobased) {
		this.zerobased = zerobased;
	}

	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
        int tot_page_cnt = 0;
        int tot_page_group_cnt = 0;
        int cur_page_group = 0;
        JspWriter out = pageContext.getOut();
        StringBuffer sbTag = new StringBuffer();
        
        if(zerobased) page_no++;

        /*
         <div class="text-center">
			<ul class="pagination">
	  			<li class="disabled"><span>≪</span></li> <!-- 클릭 안되게 하는것(disabled class를 추가하고 span 태그로 감싼다) -->
	  			<li class="disabled"><span>＜</span></li>
	  			<li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li><!-- 현재 보는 페이지 -->
	  			<li><a href="#">2</a></li>
	  			<li><a href="#">3</a></li>
	  			<li><a href="#">4</a></li>
	  			<li><a href="#">5</a></li>
	  			<li><a href="#">＞</a></li>
	  			<li><a href="#">≫</a></li>
			</ul>
		</div>
         */
        try {
            // 전체 페이지 갯수를 구한다
            if (total_cnt == 0) {
                tot_page_cnt = 0;
            } else {
                tot_page_cnt = ((total_cnt - 1) / page_size) + 1;
                tot_page_group_cnt = ((tot_page_cnt - 1) / page_group_size) + 1;
                cur_page_group = ((page_no - 1) / page_group_size) + 1;

                sbTag.append("<div class=\"text-center\">\n");
                sbTag.append("<ul class=\"pagination\">");
                
                // 1페이지를 보는 경우 1페이지로 가는 링크를 뺀다
                if (page_no == 1) {
                	sbTag.append("<li class=\"disabled\"><a href=\"javascript:void(0)\">&laquo;</a></li>");
                } else {
                	sbTag.append("<li><a href=\"javascript:void(0)\" onclick=\"" + jsFunction + "(1)\">&laquo;</a></li>");
                }
                
                // 1페이지 그룹을 보는 경우 이전 페이지 그룹 첫 페이지로 가는 링크를 뺀다
                if (cur_page_group == 1) {
                	sbTag.append("<li class=\"disabled\"><a href=\"javascript:void(0)\">&lt;</a></li>");
                } else {
                	sbTag.append("<li><a href=\"javascript:void(0)\" onclick=\"" + jsFunction + "(" + String.valueOf((cur_page_group - 2) * page_group_size + 1) + ")\">&lt;</a></li>");
                	
                }

                // 입력받은 페이지 번호를 이용하여 해당 페이지 번호가 속하는 그룹의 시작 페이지와 끝 페이지를 구한다
                int start_no = ((page_no - 1) / page_group_size) * page_group_size + 1;
                int end_no = (start_no + page_group_size) - 1;

                if (end_no > tot_page_cnt)
                    end_no = tot_page_cnt;
	  			
                for (int i = start_no; i <= end_no; i++) {
                    if (i == page_no) {   // 현재 페이지를 보는 경우
                    	sbTag.append("<li class=\"active\"><a href=\"javascript:void(0)\">" + i + "</a></li>\n");
                    } else {
                    	String clickPageNo = "";
                    	if(zerobased){
                    		clickPageNo = String.valueOf(i-1);
                    	}else{
                    		clickPageNo = String.valueOf(i);
                    	}
                    	
                    	sbTag.append("<li><a href=\"javascript:void(0)\" onclick=\"" + jsFunction + "(" + String.valueOf(clickPageNo) + ")\">" + i + "</a></li>\n");
                    	
                    }
                }
                
                // 마지막 페이지 그룹을 보는 경우 다음 페이지 그룹의 첫번째 페이지로 가는 링크를 뺀다
                if (cur_page_group == tot_page_group_cnt) {
                	sbTag.append("<li class=\"disabled\"><a href=\"javascript:void(0)\">&gt;</a></li>");
                } else {
                	sbTag.append("<li><a href=\"javascript:void(0)\" onclick=\"" + jsFunction + "(" + String.valueOf((cur_page_group) * page_group_size + 1) + ")\">&gt;</a></li>");
                }

                // 마지막 페이지를 보는 경우 마지막 페이지로 가는 링크를 뺀다
                if (page_no == tot_page_cnt) {
                	sbTag.append("<li class=\"disabled\"><a href=\"javascript:void(0)\">&raquo;</a></li>");
                } else {
                	sbTag.append("<li><a href=\"javascript:void(0)\" onclick=\"" + jsFunction + "(" + tot_page_cnt + ")\">&raquo;</a></li>");
                }

                sbTag.append("</ul>\n");
                sbTag.append("</div>\n");
                
                out.write(sbTag.toString());
            }

        } catch (Exception e) {
            throw new JspException(e);
        }
        return EVAL_PAGE;
	}

}
