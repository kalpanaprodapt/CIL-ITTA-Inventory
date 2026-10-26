import { fireEvent, render, screen, waitFor } from '@testing-library/react';
import { describe, expect, it, vi } from 'vitest';
import { VendorForm } from '../VendorForm';
import { vendorFixture } from './fixtures';

describe('VendorForm', () => {
  it('renders every field', () => {
    render(<VendorForm onSubmit={vi.fn()} />);
    expect(screen.getByRole('form', { name: 'Vendor form' })).toBeInTheDocument();
  });

  it('shows validation errors and blocks submit when empty', async () => {
    const onSubmit = vi.fn();
    render(<VendorForm onSubmit={onSubmit} />);
    fireEvent.click(screen.getByRole('button', { name: 'Save' }));
    await waitFor(() => expect(screen.getAllByText(/is required/).length).toBeGreaterThan(0));
    expect(onSubmit).not.toHaveBeenCalled();
  });

  it('submits cleaned values for an existing record', async () => {
    const onSubmit = vi.fn();
    render(<VendorForm initial={vendorFixture} onSubmit={onSubmit} submitLabel="Save changes" />);
    fireEvent.click(screen.getByRole('button', { name: 'Save changes' }));
    await waitFor(() => expect(onSubmit).toHaveBeenCalledTimes(1));
    expect(onSubmit.mock.calls[0][0].code).toBe(vendorFixture.code);
  });

  it('disables the submit button while busy', () => {
    render(<VendorForm onSubmit={vi.fn()} busy />);
    expect(screen.getByRole('button', { name: /Saving/ })).toBeDisabled();
  });

  it('shows the server error', () => {
    render(<VendorForm onSubmit={vi.fn()} serverError="Duplicate code" />);
    expect(screen.getByRole('alert')).toHaveTextContent('Duplicate code');
  });

  it('calls onCancel', () => {
    const onCancel = vi.fn();
    render(<VendorForm onSubmit={vi.fn()} onCancel={onCancel} />);
    fireEvent.click(screen.getByRole('button', { name: 'Cancel' }));
    expect(onCancel).toHaveBeenCalled();
  });
});
