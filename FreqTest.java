/**
 * Reports on how a range of sample frequencies perform with maximum
 * error for a given range of notes.
 *
 * @author Andrew E Scott
 */
public class FreqTest {
  // Sample frequencies to check
  private static final double startFreq = 20000; // Hz
  private static final double endFreq = 60000;  // Hz
  private static final double incFreq = 10;    // Hz

  // Note frequencies to check
  private static final int startNoteVal = 37;   // MIDI note number
  private static final double startNote = 220;  // Hz (A3)
  //private static final double endNote = 2350;   // Hz (just above D7)
  //private static final double endNote = 2094;   // Hz (just above C7)
  //private static final double endNote = 2638;   // Hz (just above E7)
  private static final double endNote = 2794;   // Hz (just above F7)
  private static final double multNote = Math.pow(2.0, 1.0/12);

  // Log of a cent (1/100th of the interval between semitones)
  private static final double multLogCent = Math.log(Math.pow(multNote,0.01));

  public static void main(String args[]) {
    int noteVal, errorNote;
    long notePeriod;
    double noteFreq, sampleFreq, periodFreq, errorValue, errorMax;

    // For each of the sample frequencies of interest
    for (sampleFreq = startFreq; sampleFreq <= endFreq; sampleFreq += incFreq) {
      errorMax = errorNote = 0;
      // For each note within typical flute range
      for (noteVal = startNoteVal, noteFreq = startNote; noteFreq <= endNote; noteVal++, noteFreq *= multNote) {
        // Find out the period of the note (in samples)
        notePeriod = Math.round(sampleFreq / noteFreq);
        // Find out the actual frequency corresponding to this period
        periodFreq = sampleFreq / notePeriod;
        // Calculate the error (as a fraction of the note multiple)
        errorValue = Math.abs(Math.log(periodFreq) - Math.log(noteFreq)) / multLogCent;
        if (errorValue > errorMax) {
          errorMax = errorValue;
          errorNote = noteVal;
        }
      }
      System.out.println(sampleFreq + "\t" + errorMax + "\t" + errorNote);
    }
  }
}
