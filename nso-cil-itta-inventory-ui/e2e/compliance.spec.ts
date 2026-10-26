import { expect, test } from '@playwright/test';

test.describe('Compliance and Audit', () => {
  test('Compliance Rule list renders', async ({ page }) => {
    await page.goto('/compliance/compliance-rules');
    await expect(page.getByRole('heading', { name: 'Compliance Rules' }).or(page.getByRole('heading', { name: 'Compliance Rulees' }))).toBeVisible();
  });

  test('Compliance Rule create form is reachable', async ({ page }) => {
    await page.goto('/compliance/compliance-rules/new');
    await expect(page.getByRole('form', { name: 'Compliance Rule form' })).toBeVisible();
  });

  test('Compliance Check list renders', async ({ page }) => {
    await page.goto('/compliance/compliance-checks');
    await expect(page.getByRole('heading', { name: 'Compliance Checks' }).or(page.getByRole('heading', { name: 'Compliance Checkes' }))).toBeVisible();
  });

  test('Compliance Check create form is reachable', async ({ page }) => {
    await page.goto('/compliance/compliance-checks/new');
    await expect(page.getByRole('form', { name: 'Compliance Check form' })).toBeVisible();
  });

  test('Audit Record list renders', async ({ page }) => {
    await page.goto('/compliance/audit-records');
    await expect(page.getByRole('heading', { name: 'Audit Records' }).or(page.getByRole('heading', { name: 'Audit Recordes' }))).toBeVisible();
  });

  test('Audit Record create form is reachable', async ({ page }) => {
    await page.goto('/compliance/audit-records/new');
    await expect(page.getByRole('form', { name: 'Audit Record form' })).toBeVisible();
  });

  test('Compliance Exception list renders', async ({ page }) => {
    await page.goto('/compliance/compliance-exceptions');
    await expect(page.getByRole('heading', { name: 'Compliance Exceptions' }).or(page.getByRole('heading', { name: 'Compliance Exceptiones' }))).toBeVisible();
  });

  test('Compliance Exception create form is reachable', async ({ page }) => {
    await page.goto('/compliance/compliance-exceptions/new');
    await expect(page.getByRole('form', { name: 'Compliance Exception form' })).toBeVisible();
  });

  test('Policy Document list renders', async ({ page }) => {
    await page.goto('/compliance/policy-documents');
    await expect(page.getByRole('heading', { name: 'Policy Documents' }).or(page.getByRole('heading', { name: 'Policy Documentes' }))).toBeVisible();
  });

  test('Policy Document create form is reachable', async ({ page }) => {
    await page.goto('/compliance/policy-documents/new');
    await expect(page.getByRole('form', { name: 'Policy Document form' })).toBeVisible();
  });
});
