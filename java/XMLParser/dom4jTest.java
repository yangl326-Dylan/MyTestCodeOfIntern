package XMLParser;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.STAXEventReader;
import org.dom4j.tree.DefaultDocument;
import org.springframework.beans.factory.annotation.Autowired;

//import com.panguso.op.resource.cp.core.service.downloader.DownloaderFactory;

public class dom4jTest {
	/**
	 * 属性路径
	 */
	private String fieldPath = "success";
	
	/**
	 * based on httpclient to get a InputStream
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @author Administrator
	 * @date 2014-5-21
	 */
	public InputStream getInputStream(String url) throws ClientProtocolException, IOException{
		InputStream iStream = null;

		HttpClient client = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = client.execute(httpget);
		iStream =  response.getEntity().getContent();	
		return iStream;
	}
	/**
	 * using SAXRead in dom4j
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @author Administrator
	 * @date 2014-5-21
	 */
	public Document saxReader(String url) throws ClientProtocolException, IOException{
		//download
		InputStream iStream = getInputStream(url);
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(iStream);	
			//parseXML(document);
		
			//write the doucument out to test whether get the right xml
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("out.html"), "UTF-8"));
			document.write(out);
		
		} catch (DocumentException e) {
			e.printStackTrace();
		}           
		return document;
	}
	/**
	 * 解析doucument
	 * @param doc
	 * @author Administrator
	 * @date 2014-5-21
	 */
	public void parseXML(Document doc){
		//先获得相应的Element
		Element root = doc.getRootElement();
		List<Element> elements = getElements(root);
		
		//find the values
		for (Element target : elements) {
			for (String item : this.prepareFields()) {
				Element e = target.element(item);
				if( e!= null)
				{
					//System.out.println(item + "	:\t"+ target.element(item).getText());
				}else
				{
					//System.out.println(item + "	:\t"+ " NULL ");
				}
			}
		}
	}
	/**
	 * 准备一些测试字段
	 * @return
	 * @author Administrator
	 * @date 2014-5-21
	 */
	public List<String> prepareFields(){
		List<String> list = new ArrayList<String>();
		/*list.add("pubDate");
		list.add("curUrl");
		list.add("title");
		list.add("description");*/
		list.add("success");
		return list;
	}
	/**
	 * 返回属性路径下的所有elements
	 * @param target
	 * @return
	 * @author Administrator
	 * @date 2014-5-21
	 */
	public List<Element> getElements(Object target){
		Element element = null;
		if (target instanceof DefaultDocument) {
			element = ((DefaultDocument) target).getRootElement();
		} else {
			element = (Element) target;
		}
		List<Element> elements = null;
		String[] fields = fieldPath.split("\\.");
		if (fields.length == 1) { // 如果是单层，则直接取出多条elements
			elements = element.elements(fields[0]);
		} else { // 如果是单层，先循环进入最后一层，在最后一层内取多条elements
			for (int i = 0; i < fields.length - 1; i++) {
				element = element.element(fields[i]);
			}
			elements = element.elements(fields[fields.length - 1]);
		}
		return elements;
	}
	/**
	 * 下面是测试dom4j中STaxEventReader
	 * @return
	 * @author Administrator
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws XMLStreamException 
	 * @date 2014-5-21
	 */
	public Document STaxEventReaderTest(String url) throws ClientProtocolException, IOException, XMLStreamException{
		InputStream iStream = getInputStream(url);
		STAXEventReader stax = new STAXEventReader();
		stax.readDocument(iStream);//read document from an InputStream
		iStream.close();
		return null;
	}
	//public static void main(String[] args) {
	public static void main(String[] args)  throws ClientProtocolException, IOException{
		dom4jTest s = new dom4jTest();
		String url = "http://www.amazon.cn/s/ref=sr_pg_1?rh=n%3A658390051%2Cn%3A%21658391051%2Cn%3A658394051%2Cn%3A658516051&ie=UTF8&qid=1401440979&ajr=2";
		s.saxReader(url);
		/*long start=System.currentTimeMillis();   //获取开始时间  
		for(int i=0;i<100;i++)
			s.saxReader(url);
		long end=System.currentTimeMillis(); //获取结束时间 
		System.out.println("程序运行时间： "+(end-start)+"ms");   */
		System.exit(0);
	}
}
