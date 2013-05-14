package com.ameron32.rpgbuilder;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import net.simonvt.menudrawer.MenuDrawer;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;

import com.ameron32.rpgbuilder.advantageaddon.AdvTools;
import com.ameron32.rpgbuilder.advantageaddon.AdvTools.AdvType;
import com.ameron32.rpgbuilder.advantageaddon.Advantage;
import com.ameron32.rpgbuilder.app.tools.Debugger;
import com.ameron32.rpgbuilder.app.tools.NPCRecordAdapter;
import com.ameron32.rpgbuilder.app.tools.Screen;
import com.ameron32.rpgbuilder.app.tools.Tools;
import com.ameron32.rpgbuilder.app.tools.ViewTag;
import com.ameron32.rpgbuilder.app.tools.Tools.Sort;
import com.ameron32.rpgbuilder.npcrecord.NPCRecord;
import com.ameron32.rpgbuilder.npcrecord.NPCRecordList;
import com.ameron32.rpgbuilder.npcrecord.StatRecord;
import com.ameron32.rpgbuilder.npcrecord.StatRecordGenerator;
import com.ameron32.rpgbuilder.npcrecord.components.Gender;
import com.ameron32.rpgbuilder.npcrecord.components.MoreFeatures;
import com.ameron32.rpgbuilder.npcrecord.components.MoreFeatures.FeatureType;
import com.ameron32.rpgbuilder.npcrecord.components.Note;
import com.ameron32.rpgbuilder.npcrecord.components.NoteType;

public class CharacterBuilder extends ListActivity implements OnClickListener,
		OnTouchListener {

	private CBUserInterface cbUI;
	private Sort currentSortType;

	// MenuDrawer
	private MenuDrawer mDrawer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(screenChooser());
		cbUI = new CBUserInterface();

		// MenuDrawer
		if (screenChooser() == R.layout.splashfour) {
			mDrawer = (MenuDrawer) findViewById(R.id.drawer);
			mDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_FULLSCREEN);

			findViewById(R.id.item1).setOnClickListener(this);
			findViewById(R.id.item2).setOnClickListener(this);
		}
	}

	private int screenChooser() {
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		double widthdpi = metrics.xdpi;
		double heightdpi = metrics.ydpi;
		double widthD = width / widthdpi;
		double heightD = height / heightdpi;
		double ratio = 16.0 / 9.0;
		double side1D;
		if (width < height) {
			side1D = widthD;
		} else {
			side1D = heightD;
		}
		double screenSize = Math.sqrt((side1D * side1D)
				+ ((ratio * side1D) * (ratio * side1D)));
		int activityStringS;
		if (screenSize > 6.5) {
			if (screenSize > 9.5) {
				// splashten.xml
				activityStringS = R.layout.splashten;
			} else {
				// splashseven.xml
				activityStringS = R.layout.splashseven;
			}
		} else {
			// splashfour.xml
			activityStringS = R.layout.splashfour;
		}
		return activityStringS;
	}

	@Override
	protected void onResume() {
		super.onResume();

		prepare();
		initialize();

		welcomeDialog();
		cbUI.updateDisplay();
	}

	// @Override
	// protected void onListItemClick(ListView l, View v, int position, long id)
	// {
	// super.onListItemClick(l, v, position, id);
	// Toast.makeText(CharacterBuilder.this, "Pressed" + position,
	// Toast.LENGTH_SHORT).show();
	// updateScreenForItemSelection(position);
	// }

	private void updateScreenForItemSelection(StatRecord sr) {
		statRecord = sr;
		cbUI.changeScreen(Direction.next);
		cbUI.llCGsetVisibility(View.VISIBLE);
		cbUI.updateDisplay();
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

	private void prepare() {
		checkFilesAndDirectories();
		createDirectoriesDialog();
		Tools.loadAdvantages();

		if (Assets.wipeFilesOnStart) {
			if (Assets.everythingExists)
				Tools.clearDataDirectory();
		}

		// must setListAdapter immediately, done within updateNPCRecords()
		npcRecordList = new NPCRecordList();
		cbUI.updateNPCRecords(currentSortType);
	}

	private void initialize() {
		currentSortType = Sort.none;

	}

	private Dialog whatsNewDialog, createDirectoriesDialog, advantageDialog,
			newNoteDialog;
	private TextView versionNumber, versionInfo, preVersionInfo,
			directoriesList, directoriesExplanation;
	private Button closeDialog, createDirectories, closeAndQuit,
			closeSaveNewNote, closeNoSaveNewNote;

	private void welcomeDialog() {
		if (!Tools.whatsNewExists()) {
			// initialize whatsNewDialog
			whatsNewDialog = new Dialog(this);
			whatsNewDialog.setContentView(R.layout.whatsnewdialog);
			whatsNewDialog.setTitle("New since last version...");

			closeDialog = (Button) whatsNewDialog
					.findViewById(R.id.bWhatsNewClose);
			versionNumber = (TextView) whatsNewDialog
					.findViewById(R.id.tvVerNumber);
			versionInfo = (TextView) whatsNewDialog
					.findViewById(R.id.tvVerInfo);
			preVersionInfo = (TextView) whatsNewDialog
					.findViewById(R.id.tvPreVerInfo);

			closeDialog.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// custom dialog
					Tools.whatsNewCreate();
					whatsNewDialog.dismiss();
				}
			});
			versionNumber.setText(Assets.currentVersion);
			versionInfo.setText(Assets.currentVersionNotes);
			preVersionInfo.setText(Assets.previousVersionNotes);

			whatsNewDialog.show();
		}
	}

	private void createDirectoriesDialog() {
		if (!Assets.everythingExists) {
			// initialize whatsNewDialog
			createDirectoriesDialog = new Dialog(this);
			createDirectoriesDialog
					.setContentView(R.layout.createdirectoriesdialog);
			createDirectoriesDialog.setTitle("Create Directories");

			directoriesList = (TextView) createDirectoriesDialog
					.findViewById(R.id.tvDirectoriesList);
			directoriesExplanation = (TextView) createDirectoriesDialog
					.findViewById(R.id.tvDirectoriesExplanation);
			createDirectories = (Button) createDirectoriesDialog
					.findViewById(R.id.bCreateDirectories);
			createDirectories.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// custom dialog
					for (String path : Assets.allDirectories) {
						Tools.createDirectory(path);
					}
					// Tools.createDirectories(Assets.directoryPath);
					// Tools.createDirectories(Assets.assetPath);
					File fAdv = new File(Assets.advantagesCSV);
					if (!fAdv.exists()) {
						cbUI.downloadAssets();
					}
					createDirectoriesDialog.dismiss();
				}
			});
			closeAndQuit = (Button) createDirectoriesDialog
					.findViewById(R.id.bDoNotCreateDirectories);
			closeAndQuit.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// custom dialog
					createDirectoriesDialog.dismiss();
					finish();
				}
			});
			directoriesList.setText(Assets.assetsListString);
			directoriesExplanation.setText(Assets.directoriesExplanation);

			createDirectoriesDialog.show();
		} else {
			// skip dialog
		}
	}

	private void showAdvantageDialog(Advantage a) {
		// initialize AdvantageDialog
		advantageDialog = new Dialog(this);
		advantageDialog.setContentView(R.layout.advantagedialog);
		advantageDialog.setTitle(a.getNameString() + " (" + a.getCalcCost()
				+ ")");

		// directoriesList = (TextView) advantageDialog
		// .findViewById(R.id.tvDirectoriesList);
		// directoriesExplanation = (TextView) advantageDialog
		// .findViewById(R.id.tvDirectoriesExplanation);
		closeDialog = (Button) advantageDialog
				.findViewById(R.id.bCloseAdvantageDialog);
		closeDialog.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// custom dialog
				// Tools.createDirectories(Assets.directoryPath);
				// Tools.createDirectories(Assets.assetPath);
				// File fAdv = new File(Assets.advantagesCSV);
				// if (!fAdv.exists()) {
				// downloadAssets();
				// }
				advantageDialog.dismiss();
			}
		});
		// closeAndQuit = (Button) advantageDialog
		// .findViewById(R.id.bDoNotCreateDirectories);
		// closeAndQuit.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// // custom dialog
		// advantageDialog.dismiss();
		// finish();
		// }
		// });
		// directoriesList.setText(Assets.assetsList);
		// directoriesExplanation.setText(Assets.directoriesExplanation);

		TextView advantageName = (TextView) advantageDialog
				.findViewById(R.id.tvAdvantageName);
		TextView advantageCost = (TextView) advantageDialog
				.findViewById(R.id.tvAdvantageCost);
		TextView advantageDescription = (TextView) advantageDialog
				.findViewById(R.id.tvAdvantageDescription);
		advantageName.setText(a.getNameString());
		advantageCost.setText(a.getCost());
		advantageDescription.setText(a.getDescription());

		advantageDialog.show();

	}

	private void showNewNoteDialog() {
		// initialize NewNoteDialog
		newNoteDialog = new Dialog(this);
		newNoteDialog.setContentView(R.layout.newnotedialog);
		newNoteDialog.setTitle("New Note");

		EditText noteInput = (EditText) newNoteDialog
				.findViewById(R.id.etNewNote);
		noteInput.setOnClickListener(this);

		closeSaveNewNote = (Button) newNoteDialog
				.findViewById(R.id.bCloseSaveNote);
		closeSaveNewNote.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText noteInput = (EditText) newNoteDialog
						.findViewById(R.id.etNewNote);
				String newNoteString = "";
				if (noteInput != null) {
					newNoteString = noteInput.getText().toString();
				}
				Note n = new Note(Tools.randomId(), NoteType.undefined,
						newNoteString);
				statRecord.addNote(n);
				newNoteDialog.dismiss();
				saveFile();
				cbUI.updateDisplay();
			}
		});
		closeNoSaveNewNote = (Button) newNoteDialog
				.findViewById(R.id.bCloseNoSaveNote);
		closeNoSaveNewNote.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				newNoteDialog.dismiss();
			}
		});

		newNoteDialog.show();
	}

	private void checkFilesAndDirectories() {
		// File f = new File(Assets.directoryPath);
		// File fAsset = new File(Assets.assetPath);
		boolean mustDownload = false;
		for (String s : Assets.allDirectories) {
			File f = new File(s);
			if (!f.exists()) {
				mustDownload = true;
			}
		}
		for (String s : Assets.allFiles) {
			File f = new File(s);
			if (!f.exists()) {
				mustDownload = true;
			}
		}
		if (!mustDownload) {
			Assets.everythingExists = true;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_splash, menu);
		boolean preserve = Tools.loadFileSuccessful(Assets.assetPath
				+ "preserve.all");
		menu.findItem(R.id.mPreserveNPCs).setChecked(preserve);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mPreserveNPCs:
			if (Assets.everythingExists)
				Tools.clearDataDirectory();
			cbUI.updateDisplay();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		cbUI.updateDisplay();
		return false;
	}

	@Override
	public void onBackPressed() {
		switch (cbUI.screen.getScreen()) {
		case CharacterList:
			finish();
			break;
		case RecordCard:
			cbUI.changeScreen(Direction.prev);
			break;
		case RecordCardEdit:
			cbUI.changeScreen(Direction.prev);
			break;
		default:
			finish();
			break;
		}
		cbUI.updateDisplay();
	}

	private StatRecord statRecord = null;

	private void generateCharacter(int ST, int DX, int IQ, int HT,
			Gender gender, String name, String job, List<Advantage> advantages,
			List<String> features) {

		StatRecordGenerator sg = new StatRecordGenerator(ST, DX, IQ, HT,
				gender, name, job, advantages, features);
		statRecord = sg.getStatRecord();
	}

	private void createNewCharacterPressed() {
		cbUI.changeScreen(Direction.next);
		cbUI.llCGsetVisibility(View.VISIBLE);
		cbUI.generate();
	}

	private void saveFile() {
		saveFile(statRecord);
	}

	public void saveFile(StatRecord sr) {
		String message = "";
		String dMessage = "";
		// prevent "null pointer exception"
		if (sr != null) {
			// prevent saving a malformed character
			if (sr.getST() != -1) {
				int id = -1;
				// overwrite
				if (npcRecordList.contains(sr)) {
					id = npcRecordList.findNPCRecordIdByStatRecord(sr);
					message = "Overwrote existing file!";
				}
				// new save file
				else {
					id = Tools.nextAvailableFileNumber();
					message = "Saved to a new file!";
				}
				Tools.saveFile(sr, id, Assets.ver, Assets.directoryPath);
				dMessage = sr.toString() + " | " + id + " | " + Assets.ver
						+ " | " + Assets.directoryPath;
			} else {
				message = "Character malformed. Did not save.";
			}
		} else {
			message = "No character to save.";
		}
		Toast.makeText(CharacterBuilder.this, message, Toast.LENGTH_SHORT)
				.show();
		// Debugger.addToDebuggerText(message + "\n" + dMessage, true);
	}

	private NPCRecordList npcRecordList;

	private void refreshCharacters() {
		if (Assets.advantages.isEmpty()) {
			Tools.loadAdvantages();
		}
		cbUI.updateNPCRecords(currentSortType);
	}

	float startX, startY, finalX, finalY;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startX = event.getX();
			startY = event.getY();
			break;
		case MotionEvent.ACTION_UP:
			float deadzone = 30.0f;
			boolean isLeft,
			isRight,
			isUp,
			isDown;
			isLeft = isRight = isUp = isDown = false;
			int directions = 0;
			if (startX != 0.0f && startY != 0.0f) {
				finalX = event.getX();
				finalY = event.getY();
				if (Tools.isActionLeft(startX, finalX, deadzone)) {
					isLeft = true;
					directions += 1;
				} else if (Tools.isActionRight(startX, finalX, deadzone)) {
					isRight = true;
					directions += 1;
				} else if (Tools.isActionDown(startY, finalY, deadzone)) {
					isUp = true;
					directions += 1;
				} else if (Tools.isActionUp(startY, finalY, deadzone)) {
					// do nothing
					isDown = true;
					directions += 1;
				}
			}
			if (directions > 1) {
				// which direction is primary?
				float xmovement, ymovement;
				xmovement = (startX - finalX) * 2.0f;
				ymovement = (startY - finalY) * 2.0f;
				if (xmovement > ymovement) {
					if (isLeft) {
						// left
						cbUI.changeScreen(Direction.next);
						return true;
					} else if (isRight) {
						// right
						cbUI.changeScreen(Direction.prev);
						return true;
					}
				} else {
					if (isDown) {
						// down
						// do nothing
					} else if (isUp) {
						// up
						// do nothing
					}
				}
			} else {
				if (isLeft) {
					// left
					// ui.changeScreen(Direction.next);
					// do nothing
					return true;
				} else if (isRight) {
					// right
					// ui.changeScreen(Direction.prev);
					// do nothing
					return true;
				} else if (isDown) {
					// down
					// do nothing
				} else if (isUp) {
					// up
					// do nothing
				}
			}
			break;
		}
		return false;
	}

	private void updateAdvantage(View v, int position, Advantage a,
			boolean random) {
		LinearLayout llNew = (LinearLayout) v;
		Spinner spinner = (Spinner) llNew.getChildAt(0);
		EditText cost = (EditText) llNew.getChildAt(2);

		ViewTag vt = (ViewTag) llNew.getTag();

		Advantage fixedAdv = null;
		int AdvRecordPosition = vt.getId();
		if (a == null) {
			fixedAdv = Assets.advantages.get(spinner.getSelectedItemPosition());
		} else {
			fixedAdv = a;
		}
		if (position < 0) {

		} else if (a == null) {
			String s = (String) spinner.getItemAtPosition(position);
			fixedAdv = Tools.findAdvantageByName(s);
		}

		if (random) {
			AdvType type = AdvTools.getAdvTypeForAdvantage(fixedAdv);
			// then, we generate the new advantage of the same type into that
			// slot.
			if (type != null) {
				StatRecordGenerator sg = new StatRecordGenerator();
				statRecord.getAdvantageRecord().set(AdvRecordPosition,
						sg.generateOne(type));
			}
		} else {
			int costGen = 0;
			if (!cost.getText().toString().equalsIgnoreCase("")) {
				costGen = Integer.decode(cost.getText().toString());
				if (fixedAdv.getAorD().equalsIgnoreCase("A")) {
					fixedAdv.setCalcCost(costGen);
				} else if (fixedAdv.getAorD().equalsIgnoreCase("D")) {
					fixedAdv.setCalcCost(0 - costGen);
				}
			}
			statRecord.getAdvantageRecord().set(AdvRecordPosition, fixedAdv);
		}
	}

	private void deleteAdvantage(View v) {
		ViewTag vt = (ViewTag) v.getTag();
		statRecord.getAdvantageRecord().remove(vt.getId());
	}

	// was changeFeature
	private void updateFeature(View v, boolean random) {
		StatRecordGenerator sg = new StatRecordGenerator();
		LinearLayout llRow = (LinearLayout) v;
		ViewTag vt = (ViewTag) llRow.getTag();
		EditText et = (EditText) llRow.getChildAt(0);
		// first we will determine what FeatureType the current feature is from
		// we will generate a new random feature of the same type
		// to replace this one, if a new one is not typed in.
		if (random) {
			FeatureType featureType = null;
			if (et.getText().toString().equalsIgnoreCase("")) {
				// user did not type in a different feature, so generate a
				// random
				// one
				MoreFeatures mf = new MoreFeatures();
				featureType = mf.findFeatureTypeFromString(et.getHint()
						.toString());
				// then, we generate the new feature of the same type into that
				// slot
				if (featureType != null) {
					statRecord.getFeatures().set(vt.getId(),
							sg.generateFeature(featureType));
				}
			} else {
				// user typed in a change, so update the feature to that one
				statRecord.getFeatures().set(vt.getId(),
						et.getText().toString());
			}
		} else {
			if (!et.getText().toString().equalsIgnoreCase("")) {
				// if the user has typed in another feature, but didn't click
				// change
				statRecord.getFeatures().set(vt.getId(),
						et.getText().toString());
			}
		}
	}

	// private void updateFeature(View v, boolean random) {
	// LinearLayout llRow = (LinearLayout) v;
	// ViewTag vt = (ViewTag) llRow.getTag();
	// EditText et = (EditText) llRow.getChildAt(0);
	// if (!et.getText().toString().equalsIgnoreCase("")) {
	// // if the user has typed in another feature, but didn't click
	// // change
	// statRecord.getFeatures().set(vt.getId(), et.getText().toString());
	// }
	// }

	private void deleteFeature(View v) {
		ViewTag vt = (ViewTag) v.getTag();
		statRecord.getFeatures().remove(vt.getId());
	}

	public class CBUserInterface implements OnClickListener {
		private TextView tvSortingBy;
		private TextView tvST, tvDX, tvIQ, tvHT, tvHP, tvThrust, tvSwing, tvFP,
				tvWill, tvPer, tvBS, tvMove, tvEncumbrance, tvDodge, tvParry,
				tvBlock, tvShieldDB, tvSM, tvCost, tvGender, tvName, tvClass,
				tvDebugConsole, tvAllAdv, tvAllFeatures, tvAllNotes;
		private EditText etRST, etRDX, etRIQ, etRHT, etRA, etRDA, etRPK, etRQK;
		private EditText etEName, etEClass, etEST, etEDX, etEIQ, etEHT;
		private Button bGenerate, bSave, bBackToCharacters, bAddNote;
		private Button bEditRecord, bChangeGender, bFBuild, bFHeight,
				bFComplexion, bFHair, bFHairColor, bFScent, bFAttractiveness,
				bFAge, bFImperfection, bNewAdv, bNewDis, bNewPrk, bNewQrk,
				bCommit, bCommitCancel;
		private Button bRefreshCharacters, bCreateNewCharacter, bSortDate,
				bSortName, bSortJob;
		private ViewFlipper vfSplash;
		private LinearLayout llAllAdv, llAllFeatures, llAdvs, llFeatures, llCG,
				llRCE;
		private RelativeLayout rlTouchLayout;
		private ToggleButton tbName, tbClass, tbGender;
		private ProgressBar pbCharacterGeneratedPercent;
		private TextView tvProgress;

		private Screen screen;

		private Button bExport;

		public CBUserInterface() {
			screen = new Screen(0);

			// all xmls
			rlTouchLayout = (RelativeLayout) findViewById(R.id.rlTouchLayout);
			rlTouchLayout.setOnTouchListener(CharacterBuilder.this);

			// characterlist.xml
			tvSortingBy = (TextView) findViewById(R.id.tvSortingBy);
			bRefreshCharacters = (Button) findViewById(R.id.bRefreshCharacters);
			bRefreshCharacters.setOnClickListener(this);
			bCreateNewCharacter = (Button) findViewById(R.id.bCreateNewCharacter);
			bCreateNewCharacter.setOnClickListener(this);
			bSortDate = (Button) findViewById(R.id.bSortDate);
			bSortDate.setOnClickListener(this);
			bSortName = (Button) findViewById(R.id.bSortName);
			bSortName.setOnClickListener(this);
			bSortJob = (Button) findViewById(R.id.bSortJob);
			bSortJob.setOnClickListener(this);

			// charactergen.xml
			etRST = (EditText) findViewById(R.id.etRST);
			etRST.setOnClickListener(this);
			etRDX = (EditText) findViewById(R.id.etRDX);
			etRDX.setOnClickListener(this);
			etRIQ = (EditText) findViewById(R.id.etRIQ);
			etRIQ.setOnClickListener(this);
			etRHT = (EditText) findViewById(R.id.etRHT);
			etRHT.setOnClickListener(this);
			etRA = (EditText) findViewById(R.id.etRA);
			etRA.setOnClickListener(this);
			etRDA = (EditText) findViewById(R.id.etRDA);
			etRDA.setOnClickListener(this);
			etRPK = (EditText) findViewById(R.id.etRPK);
			etRPK.setOnClickListener(this);
			etRQK = (EditText) findViewById(R.id.etRQK);
			etRQK.setOnClickListener(this);
			bGenerate = (Button) findViewById(R.id.bGenerate);
			bGenerate.setOnClickListener(this);
			bSave = (Button) findViewById(R.id.bSave);
			bSave.setOnClickListener(this);
			bBackToCharacters = (Button) findViewById(R.id.bBackToCharacters);
			bBackToCharacters.setOnClickListener(this);
			llCG = (LinearLayout) findViewById(R.id.llCG);
			llCG.setVisibility(View.INVISIBLE);
			llRCE = (LinearLayout) findViewById(R.id.llRCE);
			llRCE.setVisibility(View.INVISIBLE);
			pbCharacterGeneratedPercent = (ProgressBar) findViewById(R.id.pbCharacterGeneratedPercent);
			pbCharacterGeneratedPercent.setVisibility(View.INVISIBLE);
			tvProgress = (TextView) findViewById(R.id.tvProgress);
			tvProgress.setVisibility(View.INVISIBLE);

			// recordcard.xml
			tvST = (TextView) findViewById(R.id.tvST);
			tvDX = (TextView) findViewById(R.id.tvDX);
			tvIQ = (TextView) findViewById(R.id.tvIQ);
			tvHT = (TextView) findViewById(R.id.tvHT);
			tvHP = (TextView) findViewById(R.id.tvHP);
			tvThrust = (TextView) findViewById(R.id.tvThrust);
			tvSwing = (TextView) findViewById(R.id.tvSwing);
			tvFP = (TextView) findViewById(R.id.tvFP);
			tvWill = (TextView) findViewById(R.id.tvWill);
			tvPer = (TextView) findViewById(R.id.tvPer);
			tvBS = (TextView) findViewById(R.id.tvBS);
			tvMove = (TextView) findViewById(R.id.tvMove);
			tvEncumbrance = (TextView) findViewById(R.id.tvEncumb);
			tvDodge = (TextView) findViewById(R.id.tvDodge);
			tvParry = (TextView) findViewById(R.id.tvParry);
			tvBlock = (TextView) findViewById(R.id.tvBlock);
			tvShieldDB = (TextView) findViewById(R.id.tvShieldDB);
			tvSM = (TextView) findViewById(R.id.tvSM);
			tvCost = (TextView) findViewById(R.id.tvCost);
			tvGender = (TextView) findViewById(R.id.tvGender);
			tvName = (TextView) findViewById(R.id.tvName);
			tvClass = (TextView) findViewById(R.id.tvClass);
			llAllAdv = (LinearLayout) findViewById(R.id.llAllAdv);
			tvAllAdv = (TextView) findViewById(R.id.tvAllAdv);
			llAllFeatures = (LinearLayout) findViewById(R.id.llAllFeatures);
			tvAllFeatures = (TextView) findViewById(R.id.tvAllFeatures);
			tvAllNotes = (TextView) findViewById(R.id.tvAllNotes);
			bEditRecord = (Button) findViewById(R.id.bEditRecord);
			bEditRecord.setOnClickListener(this);
			bAddNote = (Button) findViewById(R.id.bAddNote);
			bAddNote.setOnClickListener(this);
			tbName = (ToggleButton) findViewById(R.id.tbName);
			tbName.setChecked(true);
			tbClass = (ToggleButton) findViewById(R.id.tbClass);
			tbClass.setChecked(true);
			tbGender = (ToggleButton) findViewById(R.id.tbGender);
			tbGender.setChecked(true);

			// recordcardedit.xml
			bCommit = (Button) findViewById(R.id.bCommit);
			bCommit.setOnClickListener(this);
			bCommitCancel = (Button) findViewById(R.id.bCommitCancel);
			bCommitCancel.setOnClickListener(this);
			etEName = (EditText) findViewById(R.id.etEName);
			etEClass = (EditText) findViewById(R.id.etEClass);
			bChangeGender = (Button) findViewById(R.id.bChangeGender);
			bChangeGender.setOnClickListener(this);
			etEST = (EditText) findViewById(R.id.etEST);
			etEDX = (EditText) findViewById(R.id.etEDX);
			etEIQ = (EditText) findViewById(R.id.etEIQ);
			etEHT = (EditText) findViewById(R.id.etEHT);
			OnClickListener advOCL = new OnClickListener() {
				@Override
				public void onClick(View v) {
					StatRecordGenerator sg;
					switch (v.getId()) {
					case R.id.bNewAdv:
						sg = new StatRecordGenerator();
						statRecord.getAdvantageRecord().add(
								sg.generateOne(AdvType.advantage));
						break;
					case R.id.bNewDis:
						sg = new StatRecordGenerator();
						statRecord.getAdvantageRecord().add(
								sg.generateOne(AdvType.disadvantage));
						break;
					case R.id.bNewPrk:
						sg = new StatRecordGenerator();
						statRecord.getAdvantageRecord().add(
								sg.generateOne(AdvType.perk));
						break;
					case R.id.bNewQrk:
						sg = new StatRecordGenerator();
						statRecord.getAdvantageRecord().add(
								sg.generateOne(AdvType.quirk));
						break;
					}
					updateDisplay();
				}
			};
			bNewAdv = (Button) findViewById(R.id.bNewAdv);
			bNewAdv.setOnClickListener(advOCL);
			bNewDis = (Button) findViewById(R.id.bNewDis);
			bNewDis.setOnClickListener(advOCL);
			bNewPrk = (Button) findViewById(R.id.bNewPrk);
			bNewPrk.setOnClickListener(advOCL);
			bNewQrk = (Button) findViewById(R.id.bNewQrk);
			bNewQrk.setOnClickListener(advOCL);
			OnClickListener featureOCL = new OnClickListener() {
				@Override
				public void onClick(View v) {
					MoreFeatures mf;
					switch (v.getId()) {
					case R.id.bFBuild:
						mf = new MoreFeatures();
						statRecord.getFeatures().add(
								mf.generateOneFeature(FeatureType.build));
						break;
					case R.id.bFHeight:
						mf = new MoreFeatures();
						statRecord.getFeatures().add(
								mf.generateOneFeature(FeatureType.height));
						break;
					case R.id.bFComplexion:
						mf = new MoreFeatures();
						statRecord.getFeatures().add(
								mf.generateOneFeature(FeatureType.complexion));
						break;
					case R.id.bFHair:
						mf = new MoreFeatures();
						statRecord.getFeatures().add(
								mf.generateOneFeature(FeatureType.hair));
						break;
					case R.id.bFHairColor:
						mf = new MoreFeatures();
						statRecord.getFeatures().add(
								mf.generateOneFeature(FeatureType.haircolor));
						break;
					case R.id.bFScent:
						mf = new MoreFeatures();
						statRecord.getFeatures().add(
								mf.generateOneFeature(FeatureType.scent));
						break;
					case R.id.bFAttractiveness:
						mf = new MoreFeatures();
						statRecord
								.getFeatures()
								.add(mf.generateOneFeature(FeatureType.attractiveness));
						break;
					case R.id.bFAge:
						mf = new MoreFeatures();
						statRecord.getFeatures().add(
								mf.generateOneFeature(FeatureType.age));
						break;
					case R.id.bFImperfection:
						mf = new MoreFeatures();
						statRecord
								.getFeatures()
								.add(mf.generateOneFeature(FeatureType.imperfection));
						break;
					}
					updateDisplay();
				}
			};
			bFBuild = (Button) findViewById(R.id.bFBuild);
			bFBuild.setOnClickListener(featureOCL);
			bFHeight = (Button) findViewById(R.id.bFHeight);
			bFHeight.setOnClickListener(featureOCL);
			bFComplexion = (Button) findViewById(R.id.bFComplexion);
			bFComplexion.setOnClickListener(featureOCL);
			bFHair = (Button) findViewById(R.id.bFHair);
			bFHair.setOnClickListener(featureOCL);
			bFHairColor = (Button) findViewById(R.id.bFHairColor);
			bFHairColor.setOnClickListener(featureOCL);
			bFScent = (Button) findViewById(R.id.bFScent);
			bFScent.setOnClickListener(featureOCL);
			bFAttractiveness = (Button) findViewById(R.id.bFAttractiveness);
			bFAttractiveness.setOnClickListener(featureOCL);
			bFAge = (Button) findViewById(R.id.bFAge);
			bFAge.setOnClickListener(featureOCL);
			bFImperfection = (Button) findViewById(R.id.bFImperfection);
			bFImperfection.setOnClickListener(featureOCL);

			// debugconsole.xml
			tvDebugConsole = (TextView) findViewById(R.id.tvDebugConsole);
			bExport = (Button) findViewById(R.id.bExport);
			bExport.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String result = Tools.exportAllCharacters(npcRecordList);
					Toast.makeText(CharacterBuilder.this, result,
							Toast.LENGTH_LONG).show();
				}
			});

			// resolution specific details
			if (screenChooser() == R.layout.splashfour) {
				vfSplash = (ViewFlipper) findViewById(R.id.vfSplash);
				vfSplash.setOnTouchListener(CharacterBuilder.this);
				llRCE.setOnTouchListener(CharacterBuilder.this);
				llCG.setOnTouchListener(CharacterBuilder.this);
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			} else {
				vfSplash = null;
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			}
			llAdvs = (LinearLayout) findViewById(R.id.llAdvs);
			llAdvs.setOnTouchListener(CharacterBuilder.this);
			llFeatures = (LinearLayout) findViewById(R.id.llFeatures);
			llFeatures.setOnTouchListener(CharacterBuilder.this);

			// clear softkeyboard
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(rlTouchLayout.getWindowToken(), 0);
		}

		public void llCGsetVisibility(int set) {
			llCG.setVisibility(set);
		}

		private void wipeMainScreen() {
			wipeEditText(etRST);
			wipeEditText(etRDX);
			wipeEditText(etRIQ);
			wipeEditText(etRHT);
		}

		private void wipeEditScreen() {
			wipeEditText(etEName);
			wipeEditText(etEClass);
			wipeEditText(etEST);
			wipeEditText(etEDX);
			wipeEditText(etEIQ);
			wipeEditText(etEHT);
		}

		private void wipeEditText(EditText et) {
			et.setText("");
		}

		private void changeScreen(Direction d) {
			if (d == Direction.prev) {
				previousScreen();
			} else if (d == Direction.next) {
				nextScreen();
			}
		}

		// private void changeScreen(Direction d, int screenNumber) {
		// if (d == Direction.prev) {
		// changeToPrevScreen(screenNumber);
		// } else if (d == Direction.next) {
		// changeToNextScreen(screenNumber);
		// }
		// }

		private void previousScreen() {
			if (vfSplash != null) {
				vfSplash.setInAnimation(AnimationUtils.loadAnimation(
						CharacterBuilder.this, R.anim.push_right_in));
				vfSplash.setOutAnimation(AnimationUtils.loadAnimation(
						CharacterBuilder.this, R.anim.push_right_out));

				// vfSplash.showPrevious();
				int newScreen = screen.prevScreen(screen.getScreen());
				vfSplash.setDisplayedChild(newScreen);
				screen.setScreen(newScreen);
			} else {
				// do nothing
			}
		}

		// private void changeToPrevScreen(int s) {
		// if (vfSplash != null) {
		// vfSplash.setInAnimation(AnimationUtils.loadAnimation(this,
		// R.anim.push_right_in));
		// vfSplash.setOutAnimation(AnimationUtils.loadAnimation(this,
		// R.anim.push_right_out));
		//
		// vfSplash.setDisplayedChild(s);
		// screen.setScreen(s);
		// } else {
		// // do nothing
		// }
		// }

		private void nextScreen() {
			if (vfSplash != null) {
				vfSplash.setInAnimation(AnimationUtils.loadAnimation(
						CharacterBuilder.this, R.anim.push_left_in));
				vfSplash.setOutAnimation(AnimationUtils.loadAnimation(
						CharacterBuilder.this, R.anim.push_left_out));

				// vfSplash.showNext();
				int newScreen = screen.nextScreen(screen.getScreen());
				vfSplash.setDisplayedChild(newScreen);
				screen.setScreen(newScreen);
			} else {
				// do nothing
			}
		}

		// private void changeToNextScreen(int s) {
		// if (vfSplash != null) {
		// vfSplash.setInAnimation(AnimationUtils.loadAnimation(this,
		// R.anim.push_left_in));
		// vfSplash.setOutAnimation(AnimationUtils.loadAnimation(this,
		// R.anim.push_left_out));
		//
		// vfSplash.setDisplayedChild(s);
		// screen.setScreen(s);
		// } else {
		// // do nothing
		// }
		// }

		private void editRecord() {
			// perform updatedisplayRecordCardEdit.
			// if fails (false), will not change screen (not needed).
			if (updatedisplayRecordCardEdit()) {
				changeScreen(Direction.next);
				llRCE.setVisibility(View.VISIBLE);
			} else {
				Toast.makeText(CharacterBuilder.this, "No record to edit.",
						Toast.LENGTH_SHORT).show();
			}
		}

		public void commitChanges() {
			StatRecordGenerator sg;

			// load data from EditTexts if data has been entered.
			if (etEName.length() != 0) {
				statRecord.setName(etEName.getText().toString());
			}
			if (etEClass.length() != 0) {
				statRecord.setJob(etEClass.getText().toString());
			}
			if (etEST.length() != 0) {
				statRecord.setST(Tools.sanitizeString(etEST.getText()
						.toString()));
			}
			if (etEDX.length() != 0) {
				statRecord.setDX(Tools.sanitizeString(etEDX.getText()
						.toString()));
			}
			if (etEIQ.length() != 0) {
				statRecord.setIQ(Tools.sanitizeString(etEIQ.getText()
						.toString()));
			}
			if (etEHT.length() != 0) {
				statRecord.setHT(Tools.sanitizeString(etEHT.getText()
						.toString()));
			}

			// update the advantages from the spinners and edittexts
			for (int i = 0; i < llAdvs.getChildCount(); i++) {
				LinearLayout llNew = (LinearLayout) llAdvs.getChildAt(i);
				updateAdvantage(llNew, -1, null, false);
			}

			// update the features from the edittexts, if they exist
			for (int i = 0; i < llFeatures.getChildCount(); i++) {
				LinearLayout llRow = (LinearLayout) llFeatures.getChildAt(i);
				updateFeature(llRow, false);
			}

			// salvage old id. makes "overwrite" possible.
			// otherwise, they'd all be new records
			int oldId = statRecord.getId();
			sg = new StatRecordGenerator(statRecord.getST(),
					statRecord.getDX(), statRecord.getIQ(), statRecord.getHT(),
					statRecord.getGender(), statRecord.getName(),
					statRecord.getJob(), statRecord.getAdvantageRecord(),
					statRecord.getFeatures());
			statRecord = sg.getStatRecord();
			statRecord.setId(oldId);

			// display cleanup
			wipeMainScreen();
			changeScreen(Direction.prev);
			wipeEditScreen();

			// clear softkeyboard
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(rlTouchLayout.getWindowToken(), 0);

			saveFile();
			llRCE.setVisibility(View.INVISIBLE);
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.bGenerate:
				generate();
				break;
			case R.id.bSave:
				saveFile();
				break;
			case R.id.bEditRecord:
				editRecord();
				break;
			case R.id.bCommit:
				commitChanges();
				break;
			case R.id.bCommitCancel:
				// cancel in this case means "do nothing"
				changeScreen(Direction.prev);
				wipeEditScreen();
				llRCE.setVisibility(View.INVISIBLE);
				break;
			case R.id.tbName:
				tbName.toggle();
				break;
			case R.id.tbClass:
				tbClass.toggle();
				break;
			case R.id.tbGender:
				tbGender.toggle();
				break;
			case R.id.bRefreshCharacters:
				refreshCharacters();

				// MenuDrawer implementation
				// Toast.makeText(CharacterBuilder.this, "Drawer",
				// Toast.LENGTH_SHORT).show();
				mDrawer.setActiveView(v);
				break;
			case R.id.bBackToCharacters:
				changeScreen(Direction.prev);
				llCG.setVisibility(View.INVISIBLE);
				break;
			case R.id.bCreateNewCharacter:
				createNewCharacterPressed();
				break;
			case R.id.bSortDate:
				if (currentSortType == Sort.recent10) {
					currentSortType = Sort.recent01;
				} else {
					currentSortType = Sort.recent10;
				}
				break;
			case R.id.bSortName:
				if (currentSortType == Sort.nameAZ) {
					currentSortType = Sort.nameZA;
				} else {
					currentSortType = Sort.nameAZ;
				}
				break;
			case R.id.bSortJob:
				if (currentSortType == Sort.jobAZ) {
					currentSortType = Sort.jobZA;
				} else {
					currentSortType = Sort.jobAZ;
				}
				break;
			case R.id.bChangeGender:
				Gender now = statRecord.getGender();
				if (now == Gender.male) {
					now = Gender.female;
				} else if (now == Gender.female) {
					now = Gender.male;
				}
				statRecord.setGender(now);
				break;
			case R.id.bAddNote:
				showNewNoteDialog();
				break;
			}
			updateDisplay();
		}

		public void generate() {
			GenerateCharacter gc = new GenerateCharacter(
					pbCharacterGeneratedPercent, tvProgress);
			gc.execute("");
		}

		private class GenerateCharacter extends
				AsyncTask<String, Integer, String> {

			ProgressBar pb;
			TextView tv;
			int numberOfSteps;
			int max;

			public GenerateCharacter(ProgressBar pb, TextView tv) {
				this.pb = pb;
				this.tv = tv;
			}

			@Override
			protected String doInBackground(String... string) {
				StatRecordGenerator sg;
				if (Assets.advantages.isEmpty()) {
					Tools.loadAdvantages();
				}
				if (statRecord == null) {
					sg = new StatRecordGenerator();
					statRecord = sg.getStatRecord();
				}
				publishProgress();

				// determine special conditions and load them into the temp
				// statRecord
				statRecord.setST(Tools.sanitizeString(etRST.getText()
						.toString()));
				statRecord.setDX(Tools.sanitizeString(etRDX.getText()
						.toString()));
				statRecord.setIQ(Tools.sanitizeString(etRIQ.getText()
						.toString()));
				statRecord.setHT(Tools.sanitizeString(etRHT.getText()
						.toString()));
				publishProgress();

				String reserveName = null;
				Gender reserveGender = null;
				String reserveClass = null;
				if (!tbName.isChecked()) {
					reserveName = statRecord.getName();
				}
				if (!tbGender.isChecked()) {
					reserveGender = statRecord.getGender();
				}
				if (!tbClass.isChecked()) {
					reserveClass = statRecord.getJob();
				}
				publishProgress();

				// use these conditions to generate a new character.
				// random numbers will be used where no number or bad numbers
				// are
				// provided.
				generateCharacter(statRecord.getST(), statRecord.getDX(),
						statRecord.getIQ(), statRecord.getHT(),
						statRecord.getGender(), statRecord.getName(),
						statRecord.getJob(), statRecord.getAdvantageRecord(),
						statRecord.getFeatures());
				publishProgress();

				sg = new StatRecordGenerator();
				// TODO features are not reservable, needs a checkbox like
				// name/job
				{
					statRecord.setFeatures(sg.generateFeatures());
				}
				publishProgress();

				// for the advantage module: if a number is specified,
				// generate that many.
				// otherwise a random number of them will be provided.
				{
					statRecord.setAdvantageRecord(sg.generateAdvantages(
							Tools.sanitizeString(etRA.getText().toString()),
							Tools.sanitizeString(etRDA.getText().toString()),
							Tools.sanitizeString(etRPK.getText().toString()),
							Tools.sanitizeString(etRQK.getText().toString())));
				}
				publishProgress();

				if (!tbName.isChecked()) {
					statRecord.setName(reserveName);
				} else {
					statRecord.setName(sg.generateName());
				}
				if (!tbGender.isChecked()) {
					statRecord.setGender(reserveGender);
				} else {
					statRecord.setGender(sg.generateGender());
				}
				if (!tbClass.isChecked()) {
					statRecord.setJob(reserveClass);
				} else {
					statRecord.setJob(sg.generateJob(statRecord.getGender()));
				}
				publishProgress();

				return null;
			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				pb.setVisibility(View.VISIBLE);
				tv.setVisibility(View.VISIBLE);
				tv.setText("Starting...");
				pb.setProgress(0);
				numberOfSteps = 6;
				max = 100;
				pb.setMax(max);
			}

			@Override
			protected void onProgressUpdate(Integer... progress) {
				super.onProgressUpdate(progress);
				pb.incrementProgressBy(max / numberOfSteps);
				tv.setText(progress.toString());
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				pb.setVisibility(View.INVISIBLE);
				tv.setText("");
				tv.setVisibility(View.INVISIBLE);
			}

		}

		ProgressDialog mDownloadDialog;

		private void downloadAssets() {
			// declare the dialog as a member field of your activity

			// instantiate it within the onCreate method
			mDownloadDialog = new ProgressDialog(CharacterBuilder.this);
			mDownloadDialog
					.setMessage("Downloading advantages from Dropbox...");
			mDownloadDialog.setIndeterminate(false);
			mDownloadDialog.setMax(100);
			mDownloadDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

			// execute this when the downloader must be fired
			DownloadFile downloadFile = new DownloadFile();
			downloadFile.execute("https://dl.dropbox.com/u/949753/GURPS/adv"
					+ Assets.ver + ".csv");
		}

		// usually, subclasses of AsyncTask are declared inside the activity
		// class.
		// that way, you can easily modify the UI thread from here
		private class DownloadFile extends AsyncTask<String, Integer, String> {
			@Override
			protected String doInBackground(String... sUrl) {
				try {
					URL url = new URL(sUrl[0]);
					URLConnection connection = url.openConnection();
					connection.connect();
					// this will be useful so that you can show a typical 0-100%
					// progress bar
					int fileLength = connection.getContentLength();

					// download the file
					InputStream input = new BufferedInputStream(
							url.openStream());
					OutputStream output = new FileOutputStream(Assets.assetPath
							+ "adv" + Assets.ver + ".csv");

					byte data[] = new byte[1024];
					long total = 0;
					int count;
					while ((count = input.read(data)) != -1) {
						total += count;
						// publishing the progress....
						publishProgress((int) (total * 100 / fileLength));
						output.write(data, 0, count);
					}

					output.flush();
					output.close();
					input.close();
				} catch (Exception e) {
				}
				return null;
			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				mDownloadDialog.show();
			}

			@Override
			protected void onProgressUpdate(Integer... progress) {
				super.onProgressUpdate(progress);
				mDownloadDialog.setProgress(progress[0]);
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				mDownloadDialog.dismiss();
			}

		}

		public void updateDisplay() {
			// master display checklist
			// character list
			updatedisplayCharacterList();
			updateNPCRecords(currentSortType);
			// this character
			updatedisplayRecordCard();
			updatedisplayRecordCardEdit();
			// debug
			updateDebugConsole();
		}

		private void updatedisplayCharacterList() {
			tvSortingBy.setText("Sorting By: "
					+ Tools.sortNames(currentSortType));
		}

		public void updateNPCRecords(Sort sortType) {
			if (!Assets.everythingExists) {
				checkFilesAndDirectories();
			}

			npcRecordList.clearRecords();

			NPCRecord arrayNPCRecord[] = null;
			if (Assets.everythingExists) {
				List<NPCRecord> nrs = Tools.loadNPCRecordsFromDir(
						Assets.directoryPath, Assets.ver);
				int noOfOldRecords = 0;
				for (NPCRecord nr : nrs) {
					if (nr.getVer() >= 152
							&& nr.getStatRecord().getVer() >= 152) {
						npcRecordList.addRecord(nr);
					} else {
						// TODO NO MORE UPGRADES THIS WAY
						// upgrade the record to 152
						// noOfOldRecords += 1;
						// StatRecord sr = nr.getStatRecord();
						// StatRecordGenerator sg = new StatRecordGenerator(
						// sr.getST(), sr.getDX(), sr.getIQ(), sr.getHT(),
						// sr.getGender(), sr.getName(), sr.getJob(),
						// sr.getAdvantageRecord(), sr.getFeatures());
						// NPCRecord npcRecordNew = new
						// NPCRecord(sg.getStatRecord());
						// StatRecord newSR = npcRecordNew.getStatRecord();
						// newSR.setId(sr.getId());
						// Tools.saveFile(newSR, npcRecordNew.getId(),
						// Assets.ver,
						// Assets.directoryPath);
						// npcRecordList.addRecord(npcRecordNew);
					}
				}
				if (noOfOldRecords > 0) {
					String message = " older records upgraded.";
					String noOfRecords = "" + noOfOldRecords;
					Toast.makeText(CharacterBuilder.this,
							noOfRecords + message, Toast.LENGTH_SHORT).show();
				}

				// sort chain-IF
				if (sortType == Sort.recent01) {
					npcRecordList.sortRecordsOldestFirst();
				} else if (sortType == Sort.recent10) {
					npcRecordList.sortRecordsNewestFirst();
				} else if (sortType == Sort.nameAZ) {
					npcRecordList.sortRecordsAFirst();
				} else if (sortType == Sort.nameZA) {
					npcRecordList.sortRecordsZFirst();
				} else if (sortType == Sort.jobAZ) {
					npcRecordList.sortRecordsJobAFirst();
				} else if (sortType == Sort.jobZA) {
					npcRecordList.sortRecordsJobZFirst();
				}

				arrayNPCRecord = npcRecordList.getRecords().toArray(
						new NPCRecord[npcRecordList.getRecords().size()]);
			} else {
				arrayNPCRecord = new NPCRecord[0];
			}

			// refresh the ListView that shows all the characters
			setListAdapter(new NPCRecordAdapter(CharacterBuilder.this,
					R.layout.listviewcharacter, arrayNPCRecord));

			StringBuilder sb = new StringBuilder();
			for (NPCRecord r : npcRecordList.getRecords()) {
				sb.append(r.getMyName());
				sb.append(r.getId() + "\n");
			}
			Debugger.addToDebuggerText(sb.toString(), true);
		}

		private void updatedisplayRecordCard() {
			if (statRecord != null) {
				tvST.setText(Integer.toString(statRecord.getST()));
				tvDX.setText(Integer.toString(statRecord.getDX()));
				tvIQ.setText(Integer.toString(statRecord.getIQ()));
				tvHT.setText(Integer.toString(statRecord.getHT()));
				tvWill.setText(Integer.toString(statRecord.getWill()));
				tvPer.setText(Integer.toString(statRecord.getPer()));
				tvBS.setText(Double.toString(statRecord.getBS()));
				tvDodge.setText(Integer.toString(statRecord.getDodge()));
				tvHP.setText(Integer.toString(statRecord.getHP()));
				tvFP.setText(Integer.toString(statRecord.getFP()));
				tvMove.setText(Integer.toString(statRecord.getBM()));
				tvCost.setText(Integer.toString(statRecord.getCost()));
				tvGender.setText(statRecord.getGender().name());
				tvName.setText(statRecord.getName());
				tvClass.setText(statRecord.getJob());
				tvAllFeatures.setText(Tools.buildString(
						statRecord.getFeatures(), true, false));
				llAllAdv.removeAllViews();

				for (Advantage a : statRecord.getAdvantageRecord()) {
					if (a != null) {
						Button advButton = new Button(CharacterBuilder.this);
						advButton.setTag(new ViewTag(a.getId(), "Advantage",
								"Button", a.getNameString()));
						advButton.setText("(" + a.getAorD() + ") "
								+ a.getNameString() + " [" + a.getCalcCost()
								+ "]");
						advButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								ViewTag vt = (ViewTag) v.getTag();
								Advantage a = Tools.findAdvantageById(vt
										.getId());
								showAdvantageDialog(a);
								updateDisplay();
							}
						});
						llAllAdv.addView(advButton);
					}
				}
				tvAllNotes.setText(Tools.buildString(
						statRecord.getNotes(Sort.recent10), true, true));
			}
		}

		private boolean updatedisplayRecordCardEdit() {
			boolean success = false;
			if (statRecord != null) {
				etEName.setHint(statRecord.getName());
				etEClass.setHint(statRecord.getJob());
				etEST.setHint(Integer.toString(statRecord.getST()));
				etEDX.setHint(Integer.toString(statRecord.getDX()));
				etEIQ.setHint(Integer.toString(statRecord.getIQ()));
				etEHT.setHint(Integer.toString(statRecord.getHT()));
				bChangeGender.setText(statRecord.getGender().name());
				if (llAdvs != null) {
					if (llAdvs.getChildCount() > 0) {
						llAdvs.removeAllViews();
					}
				}
				if (llFeatures != null) {
					if (llFeatures.getChildCount() > 0) {
						llFeatures.removeAllViews();
					}
				}
				List<String> advantageString = new ArrayList<String>();
				for (Advantage a : Assets.advantages) {
					advantageString.add(a.getNameString() + "["
							+ a.getCalcCost() + "]");
				}
				int p = 0;
				for (Advantage adv : statRecord.getAdvantageRecord()) {
					Spinner spinner = new Spinner(CharacterBuilder.this);
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							CharacterBuilder.this,
							android.R.layout.simple_spinner_item,
							advantageString);
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spinner.setAdapter(adapter);
					spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> parent,
								View v, int position, long id) {
							// FIXME add code here to update Advantage
							updateAdvantage((View) v.getParent().getParent(),
									position, null, false);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// do nothing
						}

					});
					int position = 0;
					for (Advantage a : Assets.advantages) {
						if (a.getId() == adv.getId()) {
							position = Assets.advantages.indexOf(a);
						}
					}

					LinearLayout llNew = new LinearLayout(CharacterBuilder.this);
					llNew.setTag(new ViewTag(p, "Advantage", "LinearLayout",
							"AdvantageRow"));
					llNew.setOrientation(LinearLayout.HORIZONTAL);
					OnClickListener advantagesOCL = new OnClickListener() {
						@Override
						public void onClick(View v) {
							ViewTag viewTag = (ViewTag) v.getTag();
							String buttonTag = viewTag.getMore();
							LinearLayout llParent = (LinearLayout) v
									.getParent();
							if (buttonTag.equalsIgnoreCase("Change")) {
								updateAdvantage(llParent, -1, null, true);
							} else if (buttonTag.equalsIgnoreCase("Delete")) {
								deleteAdvantage(llParent);
							}
							updateDisplay();
						}
					};
					{
						spinner.setSelection(position);
						spinner.setTag(new ViewTag(p, "Advantage", "Spinner",
								"AdvantageSelection"));
						spinner.setLayoutParams(new LayoutParams(dP(180.0f),
								LayoutParams.WRAP_CONTENT));
						llNew.addView(spinner);
					}
					{
						Button changeAdvantageRandom = new Button(
								CharacterBuilder.this);
						changeAdvantageRandom.setOnClickListener(advantagesOCL);
						changeAdvantageRandom.setLayoutParams(new LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT));
						changeAdvantageRandom.setText("C");
						changeAdvantageRandom.setTag(new ViewTag(p,
								"Advantage", "Button", "Change"));
						llNew.addView(changeAdvantageRandom);
					}
					{
						EditText cost = new EditText(CharacterBuilder.this);
						cost.setOnClickListener(this);
						cost.setHint(adv.getCalcCost() + "");
						cost.setInputType(InputType.TYPE_CLASS_NUMBER);
						cost.setLayoutParams(new LayoutParams(dP(70.0f),
								LayoutParams.WRAP_CONTENT));
						cost.setTag(new ViewTag(p, "Advantage", "EditText",
								"Cost"));
						llNew.addView(cost);
					}
					{
						Button deleteAdv = new Button(CharacterBuilder.this);
						deleteAdv.setOnClickListener(advantagesOCL);
						deleteAdv.setLayoutParams(new LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT));
						deleteAdv.setText("D");
						deleteAdv.setTag(new ViewTag(p, "Advantage", "Button",
								"Delete"));
						llNew.addView(deleteAdv);
					}

					llAdvs.addView(llNew);
					p++;
				}
				OnClickListener featuresOCL = new OnClickListener() {
					@Override
					public void onClick(View v) {
						ViewTag vt = (ViewTag) v.getTag();
						String buttonTag = vt.getMore();
						if (buttonTag.equalsIgnoreCase("Change")) {
							updateFeature(v, true);
						} else if (buttonTag.equalsIgnoreCase("Delete")) {
							deleteFeature(v);
						}
						updateDisplay();
					}
				};
				p = 0;
				for (String string : statRecord.getFeatures()) {
					LinearLayout llOneFeature = new LinearLayout(
							CharacterBuilder.this);
					llOneFeature.setTag(new ViewTag(p, "Feature",
							"LinearLayout", "FeatureRow"));
					llOneFeature.setOrientation(LinearLayout.HORIZONTAL);
					{
						EditText feature = new EditText(CharacterBuilder.this);
						feature.setOnClickListener(this);
						feature.setHint(string);
						feature.setLayoutParams(new LayoutParams(dP(180.0f),
								LayoutParams.WRAP_CONTENT));
						feature.setTag(new ViewTag(p, "Feature", "EditText",
								"FeatureName"));
						llOneFeature.addView(feature);
					}
					{
						TextView featureType = new TextView(
								CharacterBuilder.this);
						MoreFeatures mf = new MoreFeatures();
						FeatureType ft = mf.findFeatureTypeFromString(string);
						int end = 0;
						if (ft.name().length() < 6) {
							end = ft.name().length();
						} else {
							end = 6;
						}
						featureType.setText(ft.name().substring(0, end));
						featureType.setTextSize(9.0f);
						featureType.setLayoutParams(new LayoutParams(dP(30.0f),
								LayoutParams.WRAP_CONTENT));
						featureType.setTag(new ViewTag(p, "Feature",
								"TextView", "FeatureType"));
						llOneFeature.addView(featureType);
					}
					{
						Button changeFeature = new Button(CharacterBuilder.this);
						changeFeature.setOnClickListener(featuresOCL);
						changeFeature.setLayoutParams(new LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT));
						changeFeature.setText("C");
						changeFeature.setTag(new ViewTag(p, "Advantage",
								"Button", "Change"));
						llOneFeature.addView(changeFeature);
					}
					{
						Button deleteFeature = new Button(CharacterBuilder.this);
						deleteFeature.setOnClickListener(featuresOCL);
						deleteFeature.setLayoutParams(new LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT));
						deleteFeature.setText("D");
						deleteFeature.setTag(new ViewTag(p, "Advantage",
								"Button", "Delete"));
						llOneFeature.addView(deleteFeature);
					}
					llFeatures.addView(llOneFeature);
					p++;
				}
				success = true;
			}
			return success;
		}

		private boolean updateDebugConsole() {
			// used to read raw data when needed. not part of final design.
			StringBuilder sb = new StringBuilder();
			for (String s : Debugger.getDebuggerText()) {
				sb.append(s);
			}
			tvDebugConsole.setText(sb.toString());
			Debugger.clearDebuggerText();
			return true;
		}
	}

	public int dP(float f) {
		return Math
				.round(f
						* CharacterBuilder.this.getResources()
								.getDisplayMetrics().density);
	}

	private enum Direction {
		next, prev
	}

	@Override
	public void onClick(View v) {
		ViewTag vt = (ViewTag) v.getTag();
		int position = vt.getId();
		int npcId = npcRecordList.getRecordByPosition(position).getId();

		switch (v.getId()) {
		case R.id.rlSelectMe:
			Toast.makeText(CharacterBuilder.this, "Click: " + position + "", 
					Toast.LENGTH_LONG).show();
			StatRecord sr = npcRecordList.getRecordById(npcId).getStatRecord();
			updateScreenForItemSelection(sr);
			break;
		case R.id.bDeleteMe:
			Tools.deleteNPCRecord(npcId);
			break;
		case R.id.ibCharacterIcon:
			saveFile(Tools.upgradeNPCRecord(npcId).getStatRecord());
			break;
		}
	}
}
