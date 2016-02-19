package cn.chamatou.page.service.impl;
import org.springframework.stereotype.Service;
import cn.chamatou.page.entity.ImageAction;
import cn.chamatou.page.service.InjectDao;
import cn.chamatou.page.service.PageImageService;
@Service("pageImageService")
public class PageImageServiceImpl extends InjectDao<ImageAction> implements PageImageService{
	private static final long serialVersionUID = 3794830781988729869L;
	
}
