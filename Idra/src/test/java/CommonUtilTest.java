import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.FixMethodOrder;
import org.junit.Test;
import it.eng.idra.beans.odms.ODMSAlreadyPresentException;
import it.eng.idra.beans.odms.ODMSCatalogue;
import it.eng.idra.beans.odms.ODMSCatalogueType;
import it.eng.idra.beans.odms.ODMSManagerException;
import it.eng.idra.management.ODMSManager;
import it.eng.idra.utils.CommonUtil;
import org.junit.runners.MethodSorters;
import com.google.common.collect.Ordering;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CommonUtilTest {
	
	public ODMSCatalogue setCatalogue(String host, String homepage, String name, ODMSCatalogueType type, int id) {
		ODMSCatalogue catalogueTest = new ODMSCatalogue();
		catalogueTest.setHost(host);
		catalogueTest.setHomepage(homepage);
		catalogueTest.setName(name);
		catalogueTest.setNodeType(type);
		catalogueTest.setId(id);
		return catalogueTest;
	}
	
	ODMSCatalogue catalogueTest = setCatalogue("https://demo.getdkan.org/", "https://demo.getdkan.org/", "DKAN Test Catalogue",
			ODMSCatalogueType.DKAN, 1); 
	ODMSCatalogue catalogueTest2 = setCatalogue("https://demo.ckan.org/", "https://demo.ckan.org/", "CKAN Test Catalogue",
			ODMSCatalogueType.CKAN, 0);
	
	@Test
	public void testA_nameOrder() throws ODMSAlreadyPresentException, ODMSManagerException {

		ODMSManager.addFederatedODMSCatalogueToList(catalogueTest);
		ODMSManager.addFederatedODMSCatalogueToList(catalogueTest2);
		
		List<ODMSCatalogue> nodes  = ODMSManager.getODMSCatalogues(false);
		
		List<String> nodes_names = new ArrayList<String>();
		for(ODMSCatalogue cat: nodes) {
			nodes_names.add(cat.getName());
		}
		
		assertFalse(Ordering.from(String.CASE_INSENSITIVE_ORDER).isOrdered(nodes_names));

	   nodes  = ODMSManager.getODMSCatalogues(false)
				.stream()
				.collect(Collectors.toList());
		Collections.sort(nodes, CommonUtil.nameOrder);

		nodes_names = new ArrayList<String>();
		for(ODMSCatalogue cat: nodes) {
			nodes_names.add(cat.getName());
		}

		assertTrue(Ordering.from(String.CASE_INSENSITIVE_ORDER).isOrdered(nodes_names));
	}
	
	@Test
	public void testB_hostOrder() throws ODMSManagerException, ODMSAlreadyPresentException {

		List<ODMSCatalogue> nodes  = ODMSManager.getODMSCatalogues(false);
		
		List<String> nodes_hosts = new ArrayList<String>();
		for(ODMSCatalogue cat: nodes) {
			nodes_hosts.add(cat.getHost());
		}
		
		assertFalse(Ordering.from(String.CASE_INSENSITIVE_ORDER).isOrdered(nodes_hosts));
		
		nodes  = ODMSManager.getODMSCatalogues(false)
				.stream()
				.collect(Collectors.toList());
		Collections.sort(nodes, CommonUtil.hostOrder);

		nodes_hosts = new ArrayList<String>();
		for(ODMSCatalogue cat: nodes) {
			nodes_hosts.add(cat.getHost());
		}

		assertTrue(Ordering.from(String.CASE_INSENSITIVE_ORDER).isOrdered(nodes_hosts));
		
	}
	
	@Test
	public void testC_idOrder() throws ODMSManagerException, ODMSAlreadyPresentException {
		
		List<ODMSCatalogue> nodes  = ODMSManager.getODMSCatalogues(false);
		
		List<String> nodes_id = new ArrayList<String>();
		for(ODMSCatalogue cat: nodes) {
			nodes_id.add(String.valueOf(cat.getId()));
		}
		
		assertFalse(Ordering.from(String.CASE_INSENSITIVE_ORDER).isOrdered(nodes_id));
		
		nodes  = ODMSManager.getODMSCatalogues(false)
				.stream()
				.collect(Collectors.toList());
		Collections.sort(nodes, CommonUtil.idOrder);

		nodes_id = new ArrayList<String>();
		for(ODMSCatalogue cat: nodes) {
			nodes_id.add(String.valueOf(cat.getId()));
		}

		assertTrue(Ordering.from(String.CASE_INSENSITIVE_ORDER).isOrdered(nodes_id));
		
	}
	
	@Test
	public void testD_typeOrder() throws ODMSManagerException, ODMSAlreadyPresentException {
		
		List<ODMSCatalogue> nodes  = ODMSManager.getODMSCatalogues(false);
		
		List<String> nodes_type = new ArrayList<String>();
		for(ODMSCatalogue cat: nodes) {
			nodes_type.add(cat.getNodeType().toString());
		}
		
		assertFalse(Ordering.from(String.CASE_INSENSITIVE_ORDER).isOrdered(nodes_type));

		nodes  = ODMSManager.getODMSCatalogues(false)
				.stream()
				.collect(Collectors.toList());
		Collections.sort(nodes, CommonUtil.typeOrder);

		nodes_type = new ArrayList<String>();
		for(ODMSCatalogue cat: nodes) {
			nodes_type.add(cat.getNodeType().toString());
		}
		
		assertTrue(Ordering.from(String.CASE_INSENSITIVE_ORDER).isOrdered(nodes_type));
		
	}
	
	@Test
	public void testE_toUtcDate() {
		String year= "2021";
		String day="26";
		String mounth="07";
		
		String UTCformat = year+"-"+mounth+"-"+day+"T00:00:00Z";
		String UTCdate = CommonUtil.toUtcDate(day+"/"+mounth+"/"+year);
		
		assertEquals(UTCformat, UTCdate);
	}
	
	@Test
	public void testF_checkIfIsEmail() {
		String email = "USER01@DOMAIN.COM";
		String notEmail = "USER_DOMAIN_COM";
		
		assertTrue(CommonUtil.checkIfIsEmail(email));
		assertFalse(CommonUtil.checkIfIsEmail(notEmail));
	}
	
	@Test
	public void testG_extractFormatFromFileExtension() {
		String downloadURL = "http://www.africau.edu/images/default/sample.pdf";
		String downloadURL2 = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4";
		
		assertEquals("pdf", CommonUtil.extractFormatFromFileExtension(downloadURL));
		assertNotEquals("pdf", CommonUtil.extractFormatFromFileExtension(downloadURL2));
		assertEquals("mp4", CommonUtil.extractFormatFromFileExtension(downloadURL2));
	}
	
	


}
