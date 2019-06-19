package com.mobilepolice.office.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class PdfSimpleUtil {
    /**
     * 将图片转换成pdf文件
     * imgFilePath 需要被转换的img所存放的位置。 例如imgFilePath="D:\\projectPath\\55555.jpg";
     * pdfFilePath 转换后的pdf所存放的位置 例如pdfFilePath="D:\\projectPath\\test.pdf";
     *
     * @param image
     * @return
     * @throws IOException
     */


    public static Observable<String> imgToPdf(String imgFilePath, String pdfFilePath) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                File file = new File(imgFilePath);
                if (file.exists()) {
                    Document document = new Document();
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(pdfFilePath);
                        PdfWriter.getInstance(document, fos);

// 添加PDF文档的某些信息，比如作者，主题等等
                        document.addAuthor("arui");
                        document.addSubject("test pdf.");
// 设置文档的大小
                        document.setPageSize(PageSize.A4);
// 打开文档
                        document.open();
// 写入一段文字
//document.add(new Paragraph("JUST TEST ..."));
// 读取一个图片
                        Image image = Image.getInstance(imgFilePath);
                        float imageHeight = image.getScaledHeight();
                        float imageWidth = image.getScaledWidth();
                        int i = 0;
                        while (imageHeight > 500 || imageWidth > 500) {
                            image.scalePercent(100 - i);
                            i++;
                            imageHeight = image.getScaledHeight();
                            imageWidth = image.getScaledWidth();
                            System.out.println("imageHeight->" + imageHeight);
                            System.out.println("imageWidth->" + imageWidth);
                        }

                        image.setAlignment(Image.ALIGN_CENTER);
//     //设置图片的绝对位置
// image.setAbsolutePosition(0, 0);
// image.scaleAbsolute(500, 400);
// 插入一个图片
                        document.add(image);
                    } catch (DocumentException | IOException de) {
                        emitter.onError(de);
                    }
                    document.close();
                    fos.flush();
                    fos.close();
                    emitter.onNext(pdfFilePath);
                } else {
                    emitter.onError(new RuntimeException("file name not exists"));
                }
            }

        });
    }
}
