import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { NETWORK_DEVICE_DEVICE_ROLE_OPTIONS, NETWORK_DEVICE_LIFECYCLE_STAGE_OPTIONS, NetworkDeviceDeviceRole, NetworkDeviceLifecycleStage } from './types';
import { validateNetworkDevice, cleanNetworkDeviceValues } from './schema';
import { NETWORKDEVICE_DEFAULTS } from './types';
import type { NetworkDeviceFormValues, NetworkDeviceInput } from './types';

interface Props {
  initial?: NetworkDeviceFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: NetworkDeviceInput) => void;
  onCancel?: () => void;
}

export function NetworkDeviceForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<NetworkDeviceFormValues>({ ...NETWORKDEVICE_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof NetworkDeviceFormValues>(key: K, value: NetworkDeviceFormValues[K]) {
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
    const result = validateNetworkDevice(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanNetworkDeviceValues(values as Record<string, unknown>) as unknown as NetworkDeviceInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Network Device form">
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
      <FormField label="Hostname" htmlFor="hostname" error={errors.hostname} required={true}>
        <Input
          id="hostname"
          value={values.hostname ?? ''}
          maxLength={255}
          onChange={(v) => set('hostname', v)}
        />
      </FormField>
      <FormField label="Device Role" htmlFor="deviceRole" error={errors.deviceRole} required={false}>
        <Select
          id="deviceRole"
          value={values.deviceRole ?? ''}
          options={NETWORK_DEVICE_DEVICE_ROLE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('deviceRole', (v || undefined) as NetworkDeviceDeviceRole | undefined)}
        />
      </FormField>
      <FormField label="Mgmt Ip" htmlFor="mgmtIp" error={errors.mgmtIp} required={false}>
        <Input
          id="mgmtIp"
          value={values.mgmtIp ?? ''}
          maxLength={255}
          onChange={(v) => set('mgmtIp', v)}
        />
      </FormField>
      <FormField label="Serial Number" htmlFor="serialNumber" error={errors.serialNumber} required={false}>
        <Input
          id="serialNumber"
          value={values.serialNumber ?? ''}
          maxLength={255}
          onChange={(v) => set('serialNumber', v)}
        />
      </FormField>
      <FormField label="Model Id" htmlFor="modelId" error={errors.modelId} required={false}>
        <Input
          id="modelId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.modelId ?? ''}
          onChange={(v) => set('modelId', v)}
        />
      </FormField>
      <FormField label="Rack Unit Id" htmlFor="rackUnitId" error={errors.rackUnitId} required={false}>
        <Input
          id="rackUnitId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.rackUnitId ?? ''}
          onChange={(v) => set('rackUnitId', v)}
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
          options={NETWORK_DEVICE_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as NetworkDeviceLifecycleStage | undefined)}
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
