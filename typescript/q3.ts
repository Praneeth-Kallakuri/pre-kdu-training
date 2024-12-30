/*
  Define a generic function filterArray<T>(arr: T[], predicate: (item: T) => boolean): T[] that filters an array based on a predicate function.
  Use this function to filter an array of numbers and return only even numbers.
  Use the same function to filter an array of User objects and return users whose email includes "@company.com".

  Export the filterArray function so that the code can be tested in the test file.
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
    Suspended = "Suspended"
}

export type UserStatus = [User, Status];

// Function to print user status
export function printUserStatus(userStatus: UserStatus): void {
    const [user, status] = userStatus;
    console.log(`${user.name} is currently ${status}.`);
}

//Generic function to filter an array based on a predicate function
export function filterArray<T>(arr: T[], predicate: (item: T) => boolean): T[] {
    return arr.filter(predicate);
}
