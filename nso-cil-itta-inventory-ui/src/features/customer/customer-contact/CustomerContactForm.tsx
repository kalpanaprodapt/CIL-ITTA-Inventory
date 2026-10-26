import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { CUSTOMER_CONTACT_CONTACT_ROLE_OPTIONS, CUSTOMER_CONTACT_LIFECYCLE_STAGE_OPTIONS, CustomerContactContactRole, CustomerContactLifecycleStage } from './types';
import { validateCustomerContact, cleanCustomerContactValues } from './schema';
import { CUSTOMERCONTACT_DEFAULTS } from './types';
import type { CustomerContactFormValues, CustomerContactInput } from './types';

interface Props {
  initial?: CustomerContactFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: CustomerContactInput) => void;
  onCancel?: () => void;
}

export function CustomerContactForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<CustomerContactFormValues>({ ...CUSTOMERCONTACT_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof CustomerContactFormValues>(key: K, value: CustomerContactFormValues[K]) {
    setValues((prev) => ({ ...prev, [key]: value }));
    if (errors[key as string]) {
      setErrors((prev) => {
        const next = { ...prev };
        delete next[key as string];
        return next;
      });
    }
  }

  function submit(ev: React.FormEvent) {
    ev.preventDefault();
    const result = validateCustomerContact(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanCustomerContactValues(values as Record<string, unknown>) as unknown as CustomerContactInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Customer Contact form">
      {serverError ? <div role="alert" className="form-error">{serverError}</div> : null}
      <FormField label="Code" htmlFor="code" error={errors.code} required={true}>
        <Input
          id="code"
          value={values.code ?? ''}
          maxLength={255}
          onChange={(v) => set('code', v)}
        />
      </FormField>
      <FormField label="Name" htmlFor="name" error={errors.name} required={true}>
        <Input
          id="name"
          value={values.name ?? ''}
          maxLength={255}
          onChange={(v) => set('name', v)}
        />
      </FormField>
      <FormField label="Description" htmlFor="description" error={errors.description} required={false}>
        <TextArea
          id="description"
          value={values.description ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('description', v)}
        />
      </FormField>
      <FormField label="Status" htmlFor="status" error={errors.status} required={true}>
        <Select
          id="status"
          value={values.status ?? ''}
          options={LIFECYCLE_OPTIONS}
          allowEmpty={false}
          onChange={(v) => set('status', (v || undefined) as LifecycleStatus | undefined)}
        />
      </FormField>
      <FormField label="External Ref" htmlFor="externalRef" error={errors.externalRef} required={false}>
        <Input
          id="externalRef"
          value={values.externalRef ?? ''}
          maxLength={255}
          onChange={(v) => set('externalRef', v)}
        />
      </FormField>
      <FormField label="Customer Id" htmlFor="customerId" error={errors.customerId} required={true}>
        <Input
          id="customerId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.customerId ?? ''}
          onChange={(v) => set('customerId', v)}
        />
      </FormField>
      <FormField label="Full Name" htmlFor="fullName" error={errors.fullName} required={true}>
        <Input
          id="fullName"
          value={values.fullName ?? ''}
          maxLength={255}
          onChange={(v) => set('fullName', v)}
        />
      </FormField>
      <FormField label="Email" htmlFor="email" error={errors.email} required={false}>
        <Input
          id="email"
          value={values.email ?? ''}
          maxLength={255}
          onChange={(v) => set('email', v)}
        />
      </FormField>
      <FormField label="Phone" htmlFor="phone" error={errors.phone} required={false}>
        <Input
          id="phone"
          value={values.phone ?? ''}
          maxLength={255}
          onChange={(v) => set('phone', v)}
        />
      </FormField>
      <FormField label="Contact Role" htmlFor="contactRole" error={errors.contactRole} required={false}>
        <Select
          id="contactRole"
          value={values.contactRole ?? ''}
          options={CUSTOMER_CONTACT_CONTACT_ROLE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('contactRole', (v || undefined) as CustomerContactContactRole | undefined)}
        />
      </FormField>
      <FormField label="Labels" htmlFor="labels" error={errors.labels} required={false}>
        <Input
          id="labels"
          value={values.labels ?? ''}
          maxLength={255}
          onChange={(v) => set('labels', v)}
        />
      </FormField>
      <FormField label="Lifecycle Stage" htmlFor="lifecycleStage" error={errors.lifecycleStage} required={false}>
        <Select
          id="lifecycleStage"
          value={values.lifecycleStage ?? ''}
          options={CUSTOMER_CONTACT_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as CustomerContactLifecycleStage | undefined)}
        />
      </FormField>
      <FormField label="Source System" htmlFor="sourceSystem" error={errors.sourceSystem} required={false}>
        <Input
          id="sourceSystem"
          value={values.sourceSystem ?? ''}
          maxLength={255}
          onChange={(v) => set('sourceSystem', v)}
        />
      </FormField>
      <FormField label="Region" htmlFor="region" error={errors.region} required={false}>
        <Input
          id="region"
          value={values.region ?? ''}
          maxLength={255}
          onChange={(v) => set('region', v)}
        />
      </FormField>
      <FormField label="Owner" htmlFor="owner" error={errors.owner} required={false}>
        <Input
          id="owner"
          value={values.owner ?? ''}
          maxLength={255}
          onChange={(v) => set('owner', v)}
        />
      </FormField>
      <div className="entity-form__actions">
        {onCancel ? (
          <Button type="button" variant="ghost" onClick={onCancel}>
            Cancel
          </Button>
        ) : null}
        <Button type="submit" disabled={busy}>
          {busy ? 'Saving…' : submitLabel}
        </Button>
      </div>
    </form>
  );
}
