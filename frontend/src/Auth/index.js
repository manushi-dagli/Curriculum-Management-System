export const doLogin = (data,next) => {
    localStorage.setItem('data', JSON.stringify(data));
    if (typeof next === 'function') {
        next();
    }
}

export const isLoggedIn = () => {
    let data = localStorage.getItem('data');
    if (data == null) {
        return false;
    }
    return true;
}

export const doLogout = (next) => {
    localStorage.removeItem('data');
    localStorage.removeItem('role');
    localStorage.removeItem('pcsubject');
    localStorage.removeItem('facultysubject');
    if (typeof next === 'function') {
        next();
    }
}

export const getUserData = () => {
    if (isLoggedIn()) {
        return JSON.parse(localStorage.getItem('data'));
    }
    return null;
}