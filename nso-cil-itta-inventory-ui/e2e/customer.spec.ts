import { expect, test } from '@playwright/test';

test.describe('Customers and SLA', () => {
  test('Customer list renders', async ({ page }) => {
    await page.goto('/customer/customers');
    await expect(page.getByRole('heading', { name: 'Customers' }).or(page.getByRole('heading', { name: 'Customeres' }))).toBeVisible();
  });

  test('Customer create form is reachable', async ({ page }) => {
    await page.goto('/customer/customers/new');
    await expect(page.getByRole('form', { name: 'Customer form' })).toBeVisible();
  });

  test('Customer Account list renders', async ({ page }) => {
    await page.goto('/customer/customer-accounts');
    await expect(page.getByRole('heading', { name: 'Customer Accounts' }).or(page.getByRole('heading', { name: 'Customer Accountes' }))).toBeVisible();
  });

  test('Customer Account create form is reachable', async ({ page }) => {
    await page.goto('/customer/customer-accounts/new');
    await expect(page.getByRole('form', { name: 'Customer Account form' })).toBeVisible();
  });

  test('Customer Contact list renders', async ({ page }) => {
    await page.goto('/customer/customer-contacts');
    await expect(page.getByRole('heading', { name: 'Customer Contacts' }).or(page.getByRole('heading', { name: 'Customer Contactes' }))).toBeVisible();
  });

  test('Customer Contact create form is reachable', async ({ page }) => {
    await page.goto('/customer/customer-contacts/new');
    await expect(page.getByRole('form', { name: 'Customer Contact form' })).toBeVisible();
  });

  test('Contract list renders', async ({ page }) => {
    await page.goto('/customer/contracts');
    await expect(page.getByRole('heading', { name: 'Contracts' }).or(page.getByRole('heading', { name: 'Contractes' }))).toBeVisible();
  });

  test('Contract create form is reachable', async ({ page }) => {
    await page.goto('/customer/contracts/new');
    await expect(page.getByRole('form', { name: 'Contract form' })).toBeVisible();
  });

  test('Sla Profile list renders', async ({ page }) => {
    await page.goto('/customer/sla-profiles');
    await expect(page.getByRole('heading', { name: 'Sla Profiles' }).or(page.getByRole('heading', { name: 'Sla Profilees' }))).toBeVisible();
  });

  test('Sla Profile create form is reachable', async ({ page }) => {
    await page.goto('/customer/sla-profiles/new');
    await expect(page.getByRole('form', { name: 'Sla Profile form' })).toBeVisible();
  });
});
