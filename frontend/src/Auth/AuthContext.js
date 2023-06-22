import { createContext, useContext, useState } from "react";
import { getUserData, isLoggedIn } from ".";

const AuthContext = createContext();

export function useAuth() {
    return useContext(AuthContext);
}

export function AuthProvider({ children }) {
    const [ISLOGGEDIN, setISLOGGEDIN] = useState(isLoggedIn());
    const [USER,setUSER] = useState(getUserData());

    const LOGIN = () => {
        setISLOGGEDIN(true);
        setUSER(getUserData());
    };

    const LOGOUT = () => {
        setISLOGGEDIN(false);
        setUSER(getUserData());
    };

    return (
        <AuthContext.Provider value={{ ISLOGGEDIN, LOGIN, LOGOUT, USER }}>
            {children}
        </AuthContext.Provider>
    );
}
