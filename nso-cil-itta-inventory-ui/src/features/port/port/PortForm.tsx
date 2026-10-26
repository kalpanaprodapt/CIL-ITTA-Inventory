import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { PORT_MEDIA_TYPE_OPTIONS, PORT_ADMIN_STATE_OPTIONS, PORT_CRITICALITY_OPTIONS, PortMediaType, PortAdminState, PortCriticality } from './types';
import { validatePort, cleanPortValues } from './schema';
import { PORT_DEFAULTS } from './types';
import type { PortFormValues, PortInput } from './types';

interface Props {
  initial?: PortFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: PortInput) => void;
  onCancel?: () => void;
}

export function PortForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<PortFormValues>({ ...PORT_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof PortFormValues>(key: K, value: PortFormValues[K]) {
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
    const result = validatePort(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanPortValues(values as Record<string, unknown>) as unknown as PortInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Port form">
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
      <FormField label="Device Id" htmlFor="deviceId" error={errors.deviceId} required={true}>
        <Input
          id="deviceId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.deviceId ?? ''}
          onChange={(v) => set('deviceId', v)}
        />
      </FormField>
      <FormField label="Card Id" htmlFor="cardId" error={errors.cardId} required={false}>
        <Input
          id="cardId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.cardId ?? ''}
          onChange={(v) => set('cardId', v)}
        />
      </FormField>
      <FormField label="Port Number" htmlFor="portNumber" error={errors.portNumber} required={true}>
        <Input
          id="portNumber"
          value={values.portNumber ?? ''}
          maxLength={255}
          onChange={(v) => set('portNumber', v)}
        />
      </FormField>
      <FormField label="Speed Mbps" htmlFor="speedMbps" error={errors.speedMbps} required={false}>
        <NumberInput
          id="speedMbps"
          value={values.speedMbps}
          step={1}
          onChange={(v) => set('speedMbps', v)}
        />
      </FormField>
      <FormField label="Media Type" htmlFor="mediaType" error={errors.mediaType} required={false}>
        <Select
          id="mediaType"
          value={values.mediaType ?? ''}
          options={PORT_MEDIA_TYPE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('mediaType', (v || undefined) as PortMediaType | undefined)}
        />
      </FormField>
      <FormField label="Admin State" htmlFor="adminState" error={errors.adminState} required={false}>
        <Select
          id="adminState"
          value={values.adminState ?? ''}
          options={PORT_ADMIN_STATE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('adminState', (v || undefined) as PortAdminState | undefined)}
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
      <FormField label="Criticality" htmlFor="criticality" error={errors.criticality} required={false}>
        <Select
          id="criticality"
          value={values.criticality ?? ''}
          options={PORT_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as PortCriticality | undefined)}
        />
      </FormField>
      <FormField label="Cost Center" htmlFor="costCenter" error={errors.costCenter} required={false}>
        <Input
          id="costCenter"
          value={values.costCenter ?? ''}
          maxLength={255}
          onChange={(v) => set('costCenter', v)}
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
