
package services;

import domain.Actor;
import domain.Constant;
import domain.File;
import domain.Message;
import forms.FileForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.FileRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class FileService {

	// Managed repositories ------------------------------------------------
	@Autowired
	private FileRepository fileRepository;

	// Services
	@Autowired
	private ActorService actorService;
	@Autowired
	private MessageService messageService;

	@Autowired
	private Validator validator;

	// Constructor ----------------------------------------------------------
	public FileService() {
		super();
	}

	// Methods CRUD ---------------------------------------------------------


	public File create(final int messageId) {
		File result;
		result = new File();
		Assert.notNull(result);
		Message message = messageService.findOne(messageId);
		Assert.notNull(message);
		result.setMessage(message);
		return result;
	}

	public File findOne(final int fileId) {
		File result;

		result = this.fileRepository.findOne(fileId);
		Assert.notNull(result);

		return result;
	}



	public boolean canEditFile(final File file) {

		Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(file);

		if (false) {
			return true;
		}
		return true;
	}

	public boolean canViewFile(final File file) {

		Assert.notNull(file);

		Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(file);

		if (true) {
			return true;
		}
		return true;
	}

	public File save(final File file) {

		if (this.canEditFile(file)) {
			final File saved = this.fileRepository.save(file);
			return saved;
		}

		return null;

	}

	public void delete(final File file) {
		if (this.canEditFile(file))
			this.fileRepository.delete(file);
	}

	public File reconstruct(FileForm fileForm, BindingResult binding) {
		File resultFile;
		if (fileForm.getId() == 0) {
			switch (fileForm.getType()) {
			case Constant.FILE_TENDER:
				break;

			case Constant.FILE_TENDER_RESULT:
				break;
			default:
				break;

			}
			resultFile = this.create(fileForm.getFk());
			resultFile.setMessage(messageService.findOne(fileForm.getFk()));

			resultFile.setComment(fileForm.getComment());
			try {
				if (fileForm.getFile().isEmpty()) {
					resultFile.setName(fileForm.getName());
				} else {
					resultFile.setData(fileForm.getFile().getBytes());
					resultFile.setMimeType(fileForm.getFile().getContentType());
					Assert.isTrue(fileForm.getFile().getSize()<52428801, "file.data.size.exceeded.fail");
					resultFile.setSize(fileForm.getFile().getSize());
					resultFile.setUploadDate(new Date(new Date().getTime()-1001));
					if (fileForm.getName().isEmpty()) {
						resultFile.setName(fileForm.getFile().getOriginalFilename());
					} else {
						String originalName = fileForm.getFile().getOriginalFilename();
						String originalExtension = originalName.substring((originalName.lastIndexOf(".")==-1)?originalName.length():(originalName.lastIndexOf(".")));
						String name = fileForm.getName();
						String extension = name.substring((name.lastIndexOf(".")==-1)?name.length():(name.lastIndexOf(".")));
						extension = (originalExtension.equals(extension)) ? "" : originalExtension;
						resultFile.setName(name + extension);
					}
				}

			} catch (Throwable e) {
				this.validator.validate(resultFile, binding);
				Assert.isTrue(fileForm.getFile().getSize()<52428801, "file.data.size.exceeded.fail");
				Assert.notNull(fileForm.getFile(), "file.data.load.fail");
			}		

		} else {
			resultFile = this.findOne(fileForm.getId());
			try {
				resultFile.setComment(fileForm.getComment());
				if (fileForm.getFile().isEmpty()) {
					if (!fileForm.getName().isEmpty()) {
						String originalName = resultFile.getName();
						String originalExtension = originalName.substring((originalName.lastIndexOf(".")==-1)?originalName.length():(originalName.lastIndexOf(".")));
						String name = fileForm.getName();
						String extension = name.substring((name.lastIndexOf(".")==-1)?name.length():(name.lastIndexOf(".")));
						extension = (originalExtension.equals(extension)) ? "" : originalExtension;
						resultFile.setName(name + extension);
					}else {
						//resultFile.setName(fileForm.getName());
					}
				} else {
					resultFile.setData(fileForm.getFile().getBytes());
					resultFile.setMimeType(fileForm.getFile().getContentType());
					Assert.isTrue(fileForm.getFile().getSize()<52428801, "file.data.size.exceeded.fail");
					resultFile.setSize(fileForm.getFile().getSize());
					resultFile.setUploadDate(new Date(new Date().getTime()-1001));
					if (fileForm.getName().isEmpty()) {
						resultFile.setName(fileForm.getFile().getOriginalFilename());
					} else {
						String originalName = fileForm.getFile().getOriginalFilename();
						String originalExtension = originalName.substring(originalName.lastIndexOf("."));
						String name = fileForm.getName();
						String extension = name.substring(name.lastIndexOf("."));
						extension = (originalExtension.equals(extension)) ? "" : originalExtension;
						resultFile.setName(name + extension);
					}
				}

			} catch (Throwable e) {
				this.validator.validate(resultFile, binding);
				Assert.notNull(fileForm, "file.not.found.fail");
				Assert.notNull(fileForm.getFile(), "file.data.load.fail");
				Assert.isTrue(fileForm.getFile().getSize()<52428801, "file.data.size.exceeded.fail");
				Assert.isTrue(false, "file.data.load.fail");

			}			
		}
		this.validator.validate(resultFile, binding);
		return resultFile;
	}

	public Collection<File> findAllMessage(int messageId) {
		return fileRepository.findByMessage(messageId);
	}
}
