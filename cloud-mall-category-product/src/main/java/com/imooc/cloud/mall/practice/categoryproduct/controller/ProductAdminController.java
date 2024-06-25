package com.imooc.cloud.mall.practice.categoryproduct.controller;

import com.github.pagehelper.PageInfo;

import com.imooc.cloud.mall.practice.categoryproduct.common.ProductConstant;
import com.imooc.cloud.mall.practice.categoryproduct.model.pojo.Product;
import com.imooc.cloud.mall.practice.categoryproduct.model.request.AddProductReq;
import com.imooc.cloud.mall.practice.categoryproduct.model.request.UpdateProductReq;
import com.imooc.cloud.mall.practice.categoryproduct.service.ProductService;
import com.imooc.cloud.mall.practice.common.common.ApiRestResponse;
import com.imooc.cloud.mall.practice.common.common.Constant;
import com.imooc.cloud.mall.practice.common.exception.ImoocMallException;
import com.imooc.cloud.mall.practice.common.exception.ImoocMallExceptionEnum;
import io.swagger.annotations.ApiOperation;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 描述：     后台商品管理Controller
 */
@RestController
public class ProductAdminController {

    @Autowired
    ProductService productService;

    @Value("${file.upload.ip}")
    String ip;

    @Value("${file.upload.port}")
    Integer port;

    @PostMapping("/admin/product/add")
    public ApiRestResponse addProduct(@Valid @RequestBody AddProductReq addProductReq) {
        productService.add(addProductReq);
        return ApiRestResponse.success();
    }

    @PostMapping("/admin/upload/file")
    public ApiRestResponse upload(HttpServletRequest httpServletRequest,
            @RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //生成文件名称UUID
        UUID uuid = UUID.randomUUID();
        String newFileName = uuid.toString() + suffixName;
        //创建文件
        File fileDirectory = new File(ProductConstant.FILE_UPLOAD_DIR);
        File destFile = new File(ProductConstant.FILE_UPLOAD_DIR + newFileName);
        if (!fileDirectory.exists()) {
            if (!fileDirectory.mkdir()) {
                throw new ImoocMallException(ImoocMallExceptionEnum.MKDIR_FAILED);
            }
        }
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            // 不要遗漏统一前缀"/category-product"
            return ApiRestResponse
                    .success(getHost(new URI(httpServletRequest.getRequestURL() + "")) + "/category-product/images/"
                            + newFileName);
        } catch (URISyntaxException e) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.UPLOAD_FAILED);
        }
    }
    // 伏笔
    private URI getHost(URI uri) {
        URI effectiveURI;
        try {
            // 微服务中IP和端口号是经过网关转发的,不是直接请求的(原有服务是不直接对外暴露的,都是通过网关转发的)
            effectiveURI = new URI(uri.getScheme(), uri.getUserInfo(), ip, port,
                    null, null, null);
        } catch (URISyntaxException e) {
            effectiveURI = null;
        }
        return effectiveURI;
    }

    @ApiOperation("后台更新商品")
    @PostMapping("/admin/product/update")
    public ApiRestResponse updateProduct(@Valid @RequestBody UpdateProductReq updateProductReq) {
        Product product = new Product();
        BeanUtils.copyProperties(updateProductReq, product);
        productService.update(product);
        return ApiRestResponse.success();
    }

    @ApiOperation("后台删除商品")
    @PostMapping("/admin/product/delete")
    public ApiRestResponse deleteProduct(@RequestParam Integer id) {
        productService.delete(id);
        return ApiRestResponse.success();
    }

    @ApiOperation("后台批量上下架接口")
    @PostMapping("/admin/product/batchUpdateSellStatus")
    public ApiRestResponse batchUpdateSellStatus(@RequestParam Integer[] ids,
            @RequestParam Integer sellStatus) {
        productService.batchUpdateSellStatus(ids, sellStatus);
        return ApiRestResponse.success();
    }

    @ApiOperation("后台商品列表接口")
    @GetMapping("/admin/product/list")
    public ApiRestResponse list(@RequestParam Integer pageNum,
            @RequestParam Integer pageSize) {
        PageInfo pageInfo = productService.listForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }
}
