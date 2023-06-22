export const SubjectColumns = [
    {
        Header:'DDUcode',
        accessor:'dduCode'
    },
    {
        Header:'AICTEcode',
        accessor:'aicteCode'
    },
    {
        Header:'Semester',
        accessor:'semester'
    },
    {
        Header:'Subject Name',
        accessor:'subjectName'
    },
    {
        Header:'Effective Date',
        accessor:'effectiveDate'
    },
    {
        Header:'Removed Date',
        accessor:'removedDate'
    },
    {
        Header:'Sub Sequence',
        accessor:'subSequence'
    },
    {
        Header:'Parent Dept',
        accessor: row => row.parentDept.deptName
    },
    {
        Header:'Extra Info',
        accessor:'extraInfo'
    },
    {
        Header:'Subject Type',
        accessor:'subjectType'
    },
    {
        Header:'Subject Type Explanation',
        accessor:'subjectTypeExplanation'
    },
    {
        Header:'Theory Marks',
        accessor:'theoryMarks'
    },
    {
        Header:'Sessional Marks',
        accessor:'sessionalMarks'
    },
    {
        Header:'Termwork Marks',
        accessor:'termworkMarks'
    },
    {
        Header:'Practical Marks',
        accessor:'practicalMarks'
    },
    {
        Header:'Total Marks',
        accessor:'totalMarks'
    },
    {
        Header:'Lecture Hours',
        accessor:'lectureHours'
    },
    {
        Header:'Tutorial',
        accessor:'tutorial'
    },
    {
        Header:'Practical Hours',
        accessor:'practicalHours'
    },
    {
        Header:'Total Hours',
        accessor:'totalHours'
    },
    {
        Header:'Lecture And Theory Credit',
        accessor:'lectureAndTheoryCredit'
    },
    {
        Header:'Practical Credit',
        accessor:'practicalCredit'
    },
    {
        Header:'Total Credit',
        accessor:'totalCredit'
    },
    {
        Header:'Dept',
        accessor: row => row.dept.deptName
    },
    {
        Header:'Faculty Id',
        accessor:'facultyId'
    }
]