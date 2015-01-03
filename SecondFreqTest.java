/**
 * Reports on how different, specific sample frequencies perform with
 * maximum error as different note ranges are accommodated.
 *
 * @author Andrew E Scott
 */
public class SecondFreqTest {
  // Sample frequencies to check
  private static final double[] sampleFreqs = {
    44100.0,
    48000.0
  };

  // Note frequencies to check
  private static final int startNoteVal = 37;    // MIDI note number for A3
  private static final double startNote = 220;   // Hz (A3)
  private static final int firstMaxNoteVal = 76; // MIDI note number for C7
  private static final int lastMaxNoteVal = 88;  // MIDI note number for C8
  private static final double multNote = Math.pow(2.0, 1.0/12);

  // Log of a cent (1/100th of the interval between semitones)
  private static final double multLogCent = Math.log(Math.pow(multNote,0.01));

  public static void main(String args[]) {
    int sampleIdx, noteVal, maxNoteVal, errorNote;
    long notePeriod;
    double noteFreq, periodFreq, errorValue, errorMax;
    StringBuffer output;

    // For each of the maximum note vals
    for (maxNoteVal = firstMaxNoteVal; maxNoteVal <= lastMaxNoteVal; maxNoteVal++) {
      output = new StringBuffer();
      output.append(maxNoteVal + "\t");
      // For each of the sample frequencies of interest
      for (sampleIdx = 0; sampleIdx < sampleFreqs.length; sampleIdx++) {
        errorMax = errorNote = 0;
        // For each note within the given note range
        for (noteVal = startNoteVal, noteFreq = startNote; noteVal <= maxNoteVal; noteVal++, noteFreq *= multNote) {
          // Find out the period of the note (in samples)
          notePeriod = Math.round(sampleFreqs[sampleIdx] / noteFreq);
          // Find out the actual frequency corresponding to this period
          periodFreq = sampleFreqs[sampleIdx] / notePeriod;
          // Calculate the error (as a fraction of the note multiple)
          errorValue = Math.abs(Math.log(periodFreq) - Math.log(noteFreq)) / multLogCent;
          if (errorValue > errorMax) {
            errorMax = errorValue;
            errorNote = noteVal;
          }
        } // for noteVal <= maxNoteVal
        output.append(sampleFreqs[sampleIdx] + "\t" + errorMax + "\t");
      } // for sampleIdx < sampleFreqs.length
      System.out.println(output.toString());
    } // for maxNoteVal <= lastMaxNoteVal
  }
}
