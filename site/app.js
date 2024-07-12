const root = document.documentElement;

const fretboard = document.querySelector('.fretboard');
const instrumentSelector = document.querySelector('#instrument-selector');
const accidentalSelector = document.querySelector('.accidental-selector');
const numberOfFretsSelector = document.querySelector('#number-of-frets');
const reset = document.querySelector('.reset-button');
const tuningDisplay = document.querySelector('#tuning-display'); 
let numberOfFrets = 12;
let counter = 0;

const singleFretMarkPositions = [3, 5, 7, 9, 15, 17, 19, 21];
const doubleFretMarkPositions = [12, 24];

const notesFlat = ["A", "Bb", "B", "C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab"];
const notesSharp = ["A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"];

let accidentals = 'flats';

let result = "Chord not Found";

const constTunings = {
    'Guitar': [7, 2, 10, 5, 0, 7],
    'Bass': [10, 5, 0, 7],
    'Ukulele': [0, 7, 3, 10]
};
let instrumentTuningPresets = JSON.parse(JSON.stringify(constTunings)); 

let selectedInstrument = 'Guitar';

let numberOfStrings = instrumentTuningPresets[selectedInstrument].length;

let currentTuning = [...constTunings[selectedInstrument]];

const app = {
    init() {
        this.setupFretboard();
        this.setupInstrumentSelector();
        this.setupEventListeners();
        this.updateTuningDisplay();
    },
    setupFretboard() {
        fretboard.innerHTML = '';
        root.style.setProperty('--number-of-strings', numberOfStrings);
        for (let i = 0; i < numberOfStrings; i++) {
            let string = tools.createElement('div');
            string.classList.add(`string${i}`);
            fretboard.appendChild(string);

            for (let fret = 0; fret <= numberOfFrets; fret++) {
                let noteFret = tools.createElement('div');
                noteFret.classList.add('note-fret');
                string.appendChild(noteFret);
                if (fret === 0) {
                    let noteSelector = tools.createElement('input');
                    noteSelector.type = "number";
                    noteSelector.classList.add('input');
                    noteSelector.value = instrumentTuningPresets[selectedInstrument][i];
                    noteSelector.min = 0;
                    noteFret.style.setProperty('--noteDotOpacity', 1);
                    noteSelector.addEventListener('change', (event) => {
                        instrumentTuningPresets[selectedInstrument][i] = parseInt(event.target.value, 10);
                        currentTuning = instrumentTuningPresets[selectedInstrument];
                        this.setupFretboard();
                        this.updateTuningDisplay(); 
                        sendTuning(); 
                    });
                    noteFret.appendChild(noteSelector);
                }

                let noteName = this.generateNoteNames((fret + instrumentTuningPresets[selectedInstrument][i]));
                noteFret.setAttribute('data-note', noteName);

                if (i === 0 && singleFretMarkPositions.indexOf(fret) !== -1) {
                    noteFret.classList.add('single-fretmark');
                }

                if (i === 0 && doubleFretMarkPositions.indexOf(fret) !== -1) {
                    let doubleFretMark = tools.createElement('div');
                    doubleFretMark.classList.add('double-fretmark');
                    noteFret.appendChild(doubleFretMark);
                }
            }
        }
    },
    generateNoteNames(noteIndex) {
        noteIndex = noteIndex % 12;
        let noteName;
        if (accidentals === 'flats') {
            noteName = notesFlat[noteIndex];
        } else if (accidentals === 'sharps') {
            noteName = notesSharp[noteIndex];
        }
        return noteName;
    },
    setupInstrumentSelector() {
        let instrument = "";
        for (instrument in instrumentTuningPresets) {
            let instrumentOption = tools.createElement('option', instrument);
            instrumentOption.value = instrument; 
            instrumentSelector.appendChild(instrumentOption);
        }
    },
    setupEventListeners() {
        fretboard.addEventListener('click', (event) => {
            if (event.target.classList.contains('note-fret')) {
                const string = event.target.parentElement;
                const noteFrets = string.querySelectorAll('.note-fret');
                const stringName = string.className;
                const stringNumber = parseInt(stringName.match(/\d+/)[0], 10);
                
                const noteName = event.target.getAttribute('data-note');
                const noteDotOpacity = getComputedStyle(event.target).getPropertyValue('--noteDotOpacity').trim();
                
                if (noteDotOpacity === '1') {
                    event.target.style.setProperty('--noteDotOpacity', 0);
                    currentTuning[stringNumber] = -1;
                } else {
                    noteFrets.forEach(fret => {
                        fret.style.setProperty('--noteDotOpacity', 0);
                    });
                    event.target.style.setProperty('--noteDotOpacity', 1);
                    if (accidentals === 'flats') {
                        currentTuning[stringNumber] = notesFlat.indexOf(noteName);
                    } else if (accidentals === 'sharps') {
                        currentTuning[stringNumber] = notesSharp.indexOf(noteName);
                    }
                }

                this.updateTuningDisplay(); 
                sendTuning(); 
            }
        });
        instrumentSelector.addEventListener('change', (event) => {
            selectedInstrument = event.target.value;
            numberOfStrings = instrumentTuningPresets[selectedInstrument].length;
            currentTuning = [...constTunings[selectedInstrument]]; // Set current tuning to the preset of the selected instrument
            this.setupFretboard();
            this.updateTuningDisplay(); 
            sendTuning(); 
        });
        accidentalSelector.addEventListener('click', (event) => {
            if (event.target.classList.contains('acc-select')) {
                accidentals = event.target.value;
                this.setupFretboard();
                this.updateTuningDisplay(); 
                sendTuning(); 
            } else {
                return;
            }
        });
        numberOfFretsSelector.addEventListener('change', (event) => {
            numberOfFrets = parseInt(numberOfFretsSelector.value, 10);
            resetTuning(); 
            this.setupFretboard();
            this.updateTuningDisplay(); 
            sendTuning(); 
        });
        reset.addEventListener('click', (event) => {
            resetTuning(); 
            this.updateTuningDisplay(); 
            sendTuning(); 
        });
    },
    updateTuningDisplay() {
        tuningDisplay.value = result;
    }
};

const tools = {
    createElement(element, content) {
        element = document.createElement(element);
        if (arguments.length > 1) {
            element.innerHTML = content;
        }
        return element;
    }
};

function resetTuning() {
    instrumentTuningPresets = JSON.parse(JSON.stringify(constTunings));
    selectedInstrument = instrumentSelector.value;
    numberOfStrings = instrumentTuningPresets[selectedInstrument].length;
    currentTuning = [...instrumentTuningPresets[selectedInstrument]];
    app.setupFretboard();
    app.updateTuningDisplay(); 
    sendTuning(); 
}

async function sendTuning() {
    const response = await fetch('http://localhost:8080/api/set-tuning', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(currentTuning)
    });

    result = await response.text();
    app.updateTuningDisplay(); 
}

app.init();
