package com.CD.controller;

import com.CD.entity.api.PCViewModel;
import com.CD.entity.api.ResponseModel;
import com.CD.service.impl.ProductToChannelServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by C0dEr on 2016/12/5.
 */
@RequestMapping("channel")
@Controller
public class WebChannelController {
    @Resource
    ProductToChannelServiceImpl productToChannelService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("goodschannel");
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "relation", method = RequestMethod.POST)
    public ResponseModel getRelation(@RequestParam("id[]") List<Long> id) {
        return productToChannelService.getAllRelation(id);
    }

    @ResponseBody
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public ResponseModel getChannel() {
        return productToChannelService.getAllChannel();
    }

    @ResponseBody
    @RequestMapping(value = "product", method = RequestMethod.GET)
    public ResponseModel getProduct(@RequestParam int pageIndex, @RequestParam int pageSize) {
        return productToChannelService.getProduct(pageIndex, pageSize);
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseModel setRelation(@RequestBody List<PCViewModel> keyValue) {
        return productToChannelService.setRelation(keyValue);
    }
}
