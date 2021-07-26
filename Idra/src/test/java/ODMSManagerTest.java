import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import it.eng.idra.beans.odms.ODMSAlreadyPresentException;
import it.eng.idra.beans.odms.ODMSCatalogue;
import it.eng.idra.beans.odms.ODMSCatalogueNotFoundException;
import it.eng.idra.beans.odms.ODMSCatalogueType;
import it.eng.idra.beans.odms.ODMSManagerException;
import it.eng.idra.management.ODMSManager;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ODMSManagerTest {

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
	public void testA_addFederatedODMSCatalogueToList() throws ODMSAlreadyPresentException, ODMSManagerException, ODMSCatalogueNotFoundException { 
		
		if(ODMSManager.getODMSCatalogues(false).contains(catalogueTest))
			ODMSManager.removeFederatedODMSCatalogueFromList(catalogueTest);
		if(ODMSManager.getODMSCatalogues(false).contains(catalogueTest2))
			ODMSManager.removeFederatedODMSCatalogueFromList(catalogueTest2);
		
		assertEquals(0, ODMSManager.getODMSCatalogues(false).size());

		ODMSManager.addFederatedODMSCatalogueToList(catalogueTest);
		
		assertEquals(1, ODMSManager.getODMSCatalogues(false).size());
		
		ODMSManager.addFederatedODMSCatalogueToList(catalogueTest2);
		
		assertEquals(2, ODMSManager.getODMSCatalogues(false).size()); 
		
		
	}
	
	@Test
	public void testB_getODMSCatalogue() throws ODMSCatalogueNotFoundException, ODMSManagerException, ODMSAlreadyPresentException{

		assertEquals(catalogueTest, ODMSManager.getODMSCatalogue(catalogueTest.getId()));
		assertEquals(catalogueTest2, ODMSManager.getODMSCatalogue(catalogueTest2.getId()));

	}
	
	@Test
	public void testC_countODMSCatalogueDatasets() throws ODMSManagerException, ODMSAlreadyPresentException, ODMSCatalogueNotFoundException {
	
		catalogueTest2.setDatasetCount(1);

		assertEquals(catalogueTest2.getDatasetCount(), ODMSManager.countODMSCatalogueDatasets(catalogueTest2));
		
	}
	
	@Test
	public void testD_removeFederatedODMSCatalogueFromList() throws ODMSCatalogueNotFoundException, ODMSManagerException, ODMSAlreadyPresentException {

		
		ODMSManager.removeFederatedODMSCatalogueFromList(catalogueTest);
		
		assertEquals(1, ODMSManager.getODMSCatalogues(false).size()); 
		
		ODMSManager.removeFederatedODMSCatalogueFromList(catalogueTest2);
	
		assertEquals(0, ODMSManager.getODMSCatalogues(false).size()); 
		
	}
	
}
