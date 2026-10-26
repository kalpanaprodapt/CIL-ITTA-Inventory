import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { SERVICE_ENDPOINT_ENDPOINT_ROLE_OPTIONS, SERVICE_ENDPOINT_LIFECYCLE_STAGE_OPTIONS, ServiceEndpointEndpointRole, ServiceEndpointLifecycleStage } from './types';
import { validateServiceEndpoint, cleanServiceEndpointValues } from './schema';
import { SERVICEENDPOINT_DEFAULTS } from './types';
import type { ServiceEndpointFormValues, ServiceEndpointInput } from './types';

interface Props {
  initial?: ServiceEndpointFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: ServiceEndpointInput) => void;
  onCancel?: () => void;
}

export function ServiceEndpointForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<ServiceEndpointFormValues>({ ...SERVICEENDPOINT_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof ServiceEndpointFormValues>(key: K, value: ServiceEndpointFormValues[K]) {
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
    const result = validateServiceEndpoint(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanServiceEndpointValues(values as Record<string, unknown>) as unknown as ServiceEndpointInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Service Endpoint form">
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
      <FormField label="Service Id" htmlFor="serviceId" error={errors.serviceId} required={true}>
        <Input
          id="serviceId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.serviceId ?? ''}
          onChange={(v) => set('serviceId', v)}
        />
      </FormField>
      <FormField label="Site Id" htmlFor="siteId" error={errors.siteId} required={false}>
        <Input
          id="siteId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.siteId ?? ''}
          onChange={(v) => set('siteId', v)}
        />
      </FormField>
      <FormField label="Port Id" htmlFor="portId" error={errors.portId} required={false}>
        <Input
          id="portId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.portId ?? ''}
          onChange={(v) => set('portId', v)}
        />
      </FormField>
      <FormField label="Endpoint Role" htmlFor="endpointRole" error={errors.endpointRole} required={false}>
        <Select
          id="endpointRole"
          value={values.endpointRole ?? ''}
          options={SERVICE_ENDPOINT_ENDPOINT_ROLE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('endpointRole', (v || undefined) as ServiceEndpointEndpointRole | undefined)}
        />
      </FormField>
      <FormField label="Handoff" htmlFor="handoff" error={errors.handoff} required={false}>
        <Input
          id="handoff"
          value={values.handoff ?? ''}
          maxLength={255}
          onChange={(v) => set('handoff', v)}
        />
      </FormField>
      <FormField label="Notes" htmlFor="notes" error={errors.notes} required={false}>
        <TextArea
          id="notes"
          value={values.notes ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('notes', v)}
        />
      </FormField>
      <FormField label="Effective From" htmlFor="effectiveFrom" error={errors.effectiveFrom} required={false}>
        <DateInput
          id="effectiveFrom"
          value={values.effectiveFrom ?? ''}
          onChange={(v) => set('effectiveFrom', v)}
        />
      </FormField>
      <FormField label="Effective To" htmlFor="effectiveTo" error={errors.effectiveTo} required={false}>
        <DateInput
          id="effectiveTo"
          value={values.effectiveTo ?? ''}
          onChange={(v) => set('effectiveTo', v)}
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
          options={SERVICE_ENDPOINT_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as ServiceEndpointLifecycleStage | undefined)}
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
