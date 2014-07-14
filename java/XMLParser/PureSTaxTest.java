package XMLParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;

public class PureSTaxTest {

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
	 * 准备一些测试字段
	 * @return
	 * @author Administrator
	 * @date 2014-5-21
	 */
	public List<String> prepareFields(){
		List<String> list = new ArrayList<String>();
		list.add("pubDate");
		list.add("curUrl");
		list.add("title");
		list.add("description");
		return list;
	}
	public Document staxParse(String url) throws ClientProtocolException, IOException, XMLStreamException{
		// First, create a new XMLInputFactory
	      XMLInputFactory inputFactory = XMLInputFactory.newInstance();
	      // Setup a new eventReader
	      InputStream in = getInputStream(url);
	      XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
	      
	      //test field;
	      List<String> str = prepareFields();
	      
	      while(eventReader.hasNext()){
	    	  XMLEvent event = eventReader.nextEvent();
	    	  if (event.isStartElement()) {
	              StartElement startElement = event.asStartElement();
	              if(startElement.getName().getLocalPart().equals(str.get(0))){
	            	  event = eventReader.nextEvent();
	            	  //System.out.println(str.get(0) + "	:\t"+ event.asCharacters().getData());
	              }
	              if(startElement.getName().getLocalPart().equals(str.get(1))){
	            	  event = eventReader.nextEvent();
	            	  //System.out.println(str.get(1) + "	:\t"+ event.asCharacters().getData());
	              }
	              if(startElement.getName().getLocalPart().equals(str.get(2))){
	            	  event = eventReader.nextEvent();
	            	//  System.out.println(str.get(2) + "	:\t"+ event.asCharacters().getData());
	              }
	              if(startElement.getName().getLocalPart().equals(str.get(3))){
	            	  event = eventReader.nextEvent();
	            	  //System.out.println(str.get(3) + "	:\t"+ event.asCharacters().getData());
	              }
	              
	    	  }
	      }
	      in.close();
	      return null;
	}
	/**
	 * @param args
	 * @author Administrator
	 * @throws XMLStreamException 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @date 2014-5-21
	 */
	public static void main(String[] args) throws ClientProtocolException, IOException, XMLStreamException {
		// TODO Auto-generated method stub
		PureSTaxTest s = new PureSTaxTest();
		String url = "http://www.ifanr.com/feed";
		long start=System.currentTimeMillis();   //获取开始时间  
		for(int i=0;i<100;i++)
			s.staxParse(url);
		long end=System.currentTimeMillis(); //获取结束时间 
		System.out.println("程序运行时间： "+(end-start)+"ms");
		
		System.exit(0);
	}

}
