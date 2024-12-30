/*
  Create a utility function findById that takes an array of User objects and a number as arguments and returns a User | undefined (if the user with the given ID exists).
  Extend the function to support a new parameter, which specifies if the returned user should include undefined or throw an error. Use TypeScript's never type to represent the error-throwing case.
  Test the function using a sample array of User objects and handle both scenarios (with and without throwing an error).

  Export the function findById so that it can be used in the test file.
*/

export type User = {
    id: number;
    name: string;
    email: string;
    role?: string;
};

export interface Admin extends User {
    permissions: string[];
}

export function getProperty<T, K extends keyof T>(obj: T, key: K): T[K] {
    return obj[key];
}

export enum Status {
    Active = "Active",
    Inactive = "Inactive",
    Suspended = "Suspended",
}

export type UserStatus = [User, Status];

// Function to print user status
export function printUserStatus(userStatus: UserStatus): void {
    const [user, status] = userStatus;
    console.log(`${user.name} is currently ${status}.`);
}

// Generic function to filter an array based on a predicate function
export function filterArray<T>(arr: T[], predicate: (item: T) => boolean): T[] {
    return arr.filter(predicate);
}

// Utility function to find a user by ID
export function findById(users: User[], id: number, throwOnError: boolean = false): User | undefined {
    const user = users.find(user => user.id === id);
    if (!user && throwOnError) {
        throw new Error(`User with ID ${id} not found`);
    }
    return user;
}

